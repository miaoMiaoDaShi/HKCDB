package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.fromJson
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.InvitesResponse
import com.upholstery.share.battery.mvp.modle.entity.MessageResponse
import com.upholstery.share.battery.mvp.modle.entity.UserResponse
import com.upholstery.share.battery.mvp.presenter.InvitePresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_credits.*
import java.text.SimpleDateFormat
import java.util.logging.SimpleFormatter


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class CreditsActivity : BaseMvpActivity<MvpView, InvitePresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    override fun onLoadMoreRequested() {

        getPresenter().getInvite(0x11)
    }


    private lateinit var mAdapter: BaseQuickAdapter<InvitesResponse.DataBean, BaseViewHolder>
    override fun onRefresh() {
        getPresenter().getInvite(0x10)
    }

    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {
            }
            else -> {
            }
        }
    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> mSwipeRefresh.isRefreshing = false
            0x11 -> mAdapter.loadMoreComplete()

            else -> {
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                showSnackBar(R.string.load_error, R.string.retry) { getPresenter().getInvite(type) }
            }
            0x11 -> mAdapter.loadMoreFail()
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        if ((data as InvitesResponse).data.isEmpty()) {
            showSnackBar(R.string.no_data, Snackbar.LENGTH_INDEFINITE)
        }
        when (type) {
            0x10 -> mAdapter.replaceData(data.data)
            0x11 -> mAdapter.addData(data.data)
            else -> {
            }
        }
    }

    override fun createPresenter(): InvitePresenter = InvitePresenter()

    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
    override fun getLayoutId(): Int {

        return R.layout.activity_credits
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.credits)
                .setOnLeftImageListener { finish() }
                .setRighticon(R.drawable.ic_share)
                .showRightImage(true)
                .setOnRightImageListener { share() }

        mSwipeRefresh.setOnRefreshListener(this)
        mSwipeRefresh.post {
            mSwipeRefresh.isRefreshing = true
            onRefresh()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRvInvite.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<InvitesResponse.DataBean, BaseViewHolder>(R.layout.recycler_credits) {
            override fun convert(helper: BaseViewHolder?, item: InvitesResponse.DataBean) {
                helper?.run {
                    setText(R.id.mTvName, item.name)
                    setText(R.id.mTvTime, TimeUtils.millis2String(item.createTime, SimpleDateFormat(" yyyy-MM-dd")))
                    setText(R.id.mTvCredits, item.invitationPoint)
                }

            }
        }
        mRvInvite.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.onItemChildClickListener = this
        mAdapter.setOnLoadMoreListener(this, mRvInvite)
    }

    private fun share() {


    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {


    }

    override fun initData() {
        super.initData()
        val userResponse = fromJson<UserResponse>(mUserInfo)
        userResponse?.data?.run {
            mTvCredits.text = "${this.point}"
        }


    }


}