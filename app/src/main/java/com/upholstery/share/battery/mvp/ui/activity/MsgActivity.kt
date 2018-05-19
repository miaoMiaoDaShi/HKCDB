package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.MessageResponse
import com.upholstery.share.battery.mvp.presenter.MsgPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_recycle.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class MsgActivity : BaseMvpActivity<MvpView, MsgPresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    override fun onLoadMoreRequested() {
        getPresenter().getMag(0x11)

    }

    override fun onRefresh() {
        getPresenter().getMag(0x10)
    }

    override fun showLoading(type: Int) {


    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> mSwipeRefresh.isRefreshing = false
            else -> {
                mAdapter.loadMoreComplete()
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> showSnackBar(R.string.load_error, R.string.retry) { getPresenter().getMag(type) }
            else -> {
                mAdapter.loadMoreFail()
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        if ((data as MessageResponse).data.isEmpty()) {
            showSnackBar(R.string.no_data, Snackbar.LENGTH_INDEFINITE)
        }
        when (type) {
            0x10 -> mAdapter.replaceData(data.data)
            else -> {
                mAdapter.addData(data.data)
            }
        }
    }

    override fun createPresenter(): MsgPresenter = MsgPresenter()

    private lateinit var mAdapter: BaseQuickAdapter<MessageResponse.DataBean, BaseViewHolder>
    override fun getLayoutId(): Int {

        return R.layout.activity_recycle
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.best_new_msg)
                .setOnLeftImageListener { finish() }

        mSwipeRefresh.setOnRefreshListener(this)
        mSwipeRefresh.post {
            mSwipeRefresh.isRefreshing = true
            onRefresh()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<MessageResponse.DataBean, BaseViewHolder>(R.layout.recycler_msg) {
            override fun convert(helper: BaseViewHolder?, item: MessageResponse.DataBean) {
                helper?.apply {
                    setText(R.id.mTvContent, item.content)
                    helper.getView<ImageView>(R.id.mIvImage).load(item.content)
                }
            }
        }
        mRv.adapter = mAdapter
        mAdapter.setOnLoadMoreListener(this, mRv)

    }
}