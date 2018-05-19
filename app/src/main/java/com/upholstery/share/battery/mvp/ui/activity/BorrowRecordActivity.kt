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
import com.upholstery.share.battery.mvp.modle.entity.BorrowRecordResponse
import com.upholstery.share.battery.mvp.modle.entity.InvitesResponse
import com.upholstery.share.battery.mvp.presenter.BorrowRecordPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_borrow_record.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 借用记录(使用记录)
 *
 * 0x10 刷新
 * 0x11 加载更多
 */

class BorrowRecordActivity : BaseMvpActivity<MvpView, BorrowRecordPresenter>(),
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    /**
     * 加载更多的毁掉
     */
    override fun onLoadMoreRequested() {
        getPresenter().getBorrowRecord(0x11)

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val data = adapter.data[position] as BorrowRecordResponse.DataBean
        /**
         * 传入  id  进行详细信息查询
         */
        startActivity<BorrowRecordDetailActivity>("type" to 0x11, "id" to data.id)
    }

    private lateinit var mAdapter: BaseQuickAdapter<BorrowRecordResponse.DataBean, BaseViewHolder>

    override fun onRefresh() {
        getPresenter().getBorrowRecord(0x10)
//        mSwipeRefreshBorrowRecord.post {
//            mSwipeRefreshBorrowRecord.isRefreshing = true
//        }
    }

    override fun showLoading(type: Int) {


    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> mSwipeRefreshBorrowRecord.isRefreshing = false

            else -> {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSwipeRefreshBorrowRecord.post {
            mSwipeRefreshBorrowRecord.isRefreshing = true
            onRefresh()
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                showSnackBar(R.string.load_error, R.string.retry) { getPresenter().getBorrowRecord(type) }
            }
            0x11 -> mAdapter.loadMoreFail()
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        data as BorrowRecordResponse

        when (type) {
            0x10 -> {

                if (data.data.isEmpty()) {
                    showSnackBar(R.string.no_data, Snackbar.LENGTH_INDEFINITE)
                    mAdapter.loadMoreEnd()
                    return
                }
                mAdapter.replaceData(data.data)

            }
            0x11 -> {
                if (data.data.isEmpty()) {
                    //showSnackBar(R.string.no_more_data, Snackbar.LENGTH_SHORT)
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

    override fun createPresenter(): BorrowRecordPresenter = BorrowRecordPresenter()

    override fun getLayoutId(): Int {
        return R.layout.activity_borrow_record
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.use_record)
                .setOnLeftImageListener { finish() }
        mSwipeRefreshBorrowRecord.setOnRefreshListener(this)

        initRecycler()
    }

    /**
     * 订单状态1-使用中 2-待支付  3-已完成  4-报失 5-报损',
     */

    private fun initRecycler() {
        val types = resources.getStringArray(R.array.borrow_status)
        val typeColors = arrayOf(R.color.yellow,R.color.yellow,
                R.color.black, R.color.black, R.color.black)
        mRvBorrowRecord.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = object : BaseQuickAdapter<BorrowRecordResponse.DataBean, BaseViewHolder>(R
                .layout.recycler_borrow_recode) {
            override fun convert(helper: BaseViewHolder, item: BorrowRecordResponse.DataBean) {
                helper.setText(R.id.tvCreatTime, TimeUtils.millis2String(item.createTime, SimpleDateFormat(" yyyy-MM-dd")))
                        .setText(R.id.tvUseTime, "${item.used}分")
                        //.setText(R.id.tvMoney, String.format("%.2f", item.cost / 100.0))
                        .setVisible(R.id.tvMoney,false)
                        .setText(R.id.tvStatus, types[item.statusX - 1])
                        .setTextColor(R.id.tvStatus, getColor(typeColors[item.statusX - 1]))
                        .addOnClickListener(R.id.tvOperation)
            }
        }
        mRvBorrowRecord.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.onItemChildClickListener = this
        mAdapter.setOnLoadMoreListener(this, mRvBorrowRecord)
    }


}