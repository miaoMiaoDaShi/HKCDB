package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CurrencyRecordListResponse
import com.upholstery.share.battery.mvp.presenter.CreditsAndCurrencyPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_currency_record.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/8
 * Description : 貨幣記錄
 */
class CurrencyRecordActivity : BaseMvpActivity<MvpView, CreditsAndCurrencyPresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    override fun onRefresh() {
        getPresenter().getCurrencyRecordList(0x10)
    }

    override fun getLayoutId(): Int = R.layout.activity_currency_record

    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {//刷新
            }
            0x11 -> {//加載更多

            }
            else -> {
            }
        }
    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> {//刷新
                mSwipeRefresh.isRefreshing = false
            }
            0x11 -> {//加載更多

            }
            else -> {
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {//刷新

            }
            0x11 -> {//加載更多

            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {//刷新
                data as CurrencyRecordListResponse
                mAdapter.replaceData(data.data)
                if (data.data.isEmpty()) {
                    mAdapter.loadMoreEnd()
                }
            }
            0x11 -> {//加載更多
                data as CurrencyRecordListResponse
                if (data.data.isEmpty()) {
                    mAdapter.loadMoreEnd()
                    return
                }
                mAdapter.addData(data.data)
                mAdapter.loadMoreComplete()
            }
            else -> {
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.currency_record)
                .setOnLeftImageListener { finish() }
        mSwipeRefresh.setOnRefreshListener(this)
        initRecyclerView()
    }

    private lateinit var mAdapter: BaseQuickAdapter<CurrencyRecordListResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
        val types = resources.getStringArray(R.array.currency_types)
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<CurrencyRecordListResponse.DataBean, BaseViewHolder>(R.layout.recycler_currency_record) {
            override fun convert(helper: BaseViewHolder, item: CurrencyRecordListResponse.DataBean) {
                helper.setText(R.id.mTvTime,
                        TimeUtils.millis2String(item.createTime, SimpleDateFormat(" yyyy-MM-dd")))
                        .setText(R.id.mTvType, types[item.type])//0-赠送 1-消费',
                        .setText(R.id.mTvCount, if (item.type == 0) "+${item.money / 100}" else "-${item.money / 100}")

            }

        }
        mRv.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener(this, mRv)

    }

    override fun onLoadMoreRequested() {
        getPresenter().getCurrencyRecordList(0x11)

    }

    override fun start() {
        super.start()
        mSwipeRefresh.post {
            mSwipeRefresh.isRefreshing = true
            getPresenter().getCurrencyRecordList(0x10)
        }

    }

    override fun createPresenter(): CreditsAndCurrencyPresenter = CreditsAndCurrencyPresenter()
}