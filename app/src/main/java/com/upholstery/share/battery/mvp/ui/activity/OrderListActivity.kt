package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.MoneyRecordResponse
import com.upholstery.share.battery.mvp.presenter.OrderPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_order_list.*
import java.text.SimpleDateFormat


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 *  0x11  刷新
 *  0x12 加载更多
 */

class OrderListActivity : BaseMvpActivity<MvpView, OrderPresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {


    }

    override fun showLoading(type: Int) {
        when (type) {
            0x11 -> {

            }
            else -> {
            }
        }
    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x11 -> {
                mSwipeRefreshOrderList.isRefreshing = false
            }
            else -> {
                mAdapter.loadMoreComplete()
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x11 -> {
                mSwipeRefreshOrderList.isRefreshing = false
                showSnackBar(R.string.load_error, R.string.retry) { onRefresh() }
            }
            else -> {
                mAdapter.loadMoreFail()
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        if ((data as MoneyRecordResponse).data.isEmpty()) {
            showSnackBar(R.string.no_data, Snackbar.LENGTH_INDEFINITE)
        }
        when (type) {
            0x11 -> mAdapter.replaceData(data.data)
            else -> {
                mAdapter.addData(data.data)
            }
        }
    }

    override fun createPresenter(): OrderPresenter = OrderPresenter()

    private lateinit var mAdapter: BaseQuickAdapter<MoneyRecordResponse.DataBean, BaseViewHolder>
    override fun onRefresh() {
        mSwipeRefreshOrderList.post {
            mSwipeRefreshOrderList.isRefreshing = true
        }
        getPresenter().getOrder(0x11)

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_order_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.detail)
                .setOnLeftImageListener { finish() }

        mSwipeRefreshOrderList.setOnRefreshListener(this)
        initRecycler()
        onRefresh()
    }

    /**
     * 0-充值 1-提现 2-收益 3-消费 4-购买会员
     */
    private val mTypes = arrayOf("充值", "提现", "收益", "消费", "购买会员")

    private fun initRecycler() {

        mRvOrderList.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<MoneyRecordResponse.DataBean, BaseViewHolder>(R.layout.recycler_borrow_recode) {
            override fun convert(helper: BaseViewHolder, item: MoneyRecordResponse.DataBean) {
                helper.setText(R.id.tvTime, TimeUtils.millis2String(item.create_time, SimpleDateFormat(" yyyy-MM-dd")))
                        .setText(R.id.tvMoney, String.format("%.2f", item.money / 100.00))
                        .setText(R.id.tvType, mTypes[item.type])
            }

        }
        mAdapter.onItemChildClickListener = this
        mAdapter.setEnableLoadMore(true)
        mRvOrderList.adapter = mAdapter
    }
}