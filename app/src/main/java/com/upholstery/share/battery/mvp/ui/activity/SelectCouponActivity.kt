package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CouponResponse
import com.upholstery.share.battery.mvp.presenter.CouponPresenter
import kotlinx.android.synthetic.main.activity_recycle.*
import java.text.SimpleDateFormat

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/2
 * Description : 优惠券的选择
 */
class SelectCouponActivity : BaseMvpActivity<MvpView, CouponPresenter>(),
SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {


    override fun onRefresh() {
        getPresenter().getCoupon(3)

    }

    override fun getLayoutId(): Int = R.layout.activity_recycle
    private lateinit var mAdapter: BaseQuickAdapter<CouponResponse.DataBean, BaseViewHolder>
    override fun showLoading(type: Int) {
    }

    override fun dismissLoading(type: Int) {
        mSwipeRefresh.isRefreshing = false
    }

    override fun handlerError(type: Int, e: String) {
        toast(e)
    }

    override fun handlerSuccess(type: Int, data: Any) {
        if ((data as CouponResponse).data.isEmpty()) {
            showSnackBar(R.string.no_data, Snackbar.LENGTH_LONG)
        }
        mAdapter.replaceData(data.data)
    }

    override fun createPresenter(): CouponPresenter  = CouponPresenter()


    override fun start() {
        super.start()

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mSwipeRefresh.setOnRefreshListener(this)
        mSwipeRefresh.post {
            onRefresh()
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRv.layoutManager = LinearLayoutManager(this)
        mAdapter = object : BaseQuickAdapter<CouponResponse.DataBean, BaseViewHolder>(R.layout.recycler_coupon) {
            override fun convert(helper: BaseViewHolder, item: CouponResponse.DataBean) {
                helper.setText(R.id.tvCount,"${item.worthMoney/100}")
                        .setText(R.id.tvTime, TimeUtils.millis2String(item.endTime, SimpleDateFormat(" yyyy-MM-dd")))

            }

        }
        mAdapter.onItemClickListener = this
        mRv.adapter = mAdapter

    }
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val data = adapter.data[position] as CouponResponse.DataBean
        selectCoupon("${data.id}",data.worthMoney)

    }

    /**
     * 选择优惠券
     * @param 优惠券Id
     * @param 减免金额(分)
     */
    private fun selectCoupon(couponId: String, money: Int) {
        val intent = Intent()
        intent.putExtra("id", couponId)
        intent.putExtra("money", money)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}