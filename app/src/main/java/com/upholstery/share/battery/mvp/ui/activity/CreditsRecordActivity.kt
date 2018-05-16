package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CreditRecordListResponse
import com.upholstery.share.battery.mvp.modle.entity.CurrencyRecordListResponse
import com.upholstery.share.battery.mvp.presenter.CreditsAndCurrencyPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_currency_record.*
import org.jetbrains.anko.find
import java.text.SimpleDateFormat

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/8
 * Description : 積分記錄
 */
class CreditsRecordActivity : BaseMvpActivity<MvpView, CreditsAndCurrencyPresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


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
                data as CreditRecordListResponse
                mAdapter.replaceData(data.data)
                if (data.data.isEmpty()) {
                    showSnackBar(R.string.no_data)
                    mAdapter.loadMoreEnd()
                }
            }
            0x11 -> {//加載更多
                data as CreditRecordListResponse
                if (data.data.isEmpty()) {
                    showSnackBar(R.string.no_more_data)
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
                .setTitle(R.string.credits_record)
                .setOnLeftImageListener { finish() }
        mTvType.text = getString(R.string.credits_count)
        mSwipeRefresh.setOnRefreshListener(this)
        initRecyclerView()
    }

    private lateinit var mAdapter: BaseQuickAdapter<CreditRecordListResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
        val types = resources.getStringArray(R.array.currency_types)
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<CreditRecordListResponse.DataBean, BaseViewHolder>(R.layout.recycler_currency_record) {
            override fun convert(helper: BaseViewHolder, item: CreditRecordListResponse.DataBean) {
                helper.setText(R.id.mTvTime,
                        TimeUtils.millis2String(item.createTime, SimpleDateFormat(" yyyy-MM-dd")))
                        .setText(R.id.mTvType, item.usedTo)
                        .setText(R.id.mTvCount, "${item.point}")
                        .getView<TextView>(R.id.mTvType).isSelected = true
            }

        }
        mRv.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener(this, mRv)

    }

    override fun onLoadMoreRequested() {
        getPresenter().getCreditRecordList(0x11)

    }

    override fun start() {
        super.start()
        mSwipeRefresh.post {
            mSwipeRefresh.isRefreshing = true
            getPresenter().getCreditRecordList(0x10)
        }

    }

    override fun createPresenter(): CreditsAndCurrencyPresenter = CreditsAndCurrencyPresenter()
}