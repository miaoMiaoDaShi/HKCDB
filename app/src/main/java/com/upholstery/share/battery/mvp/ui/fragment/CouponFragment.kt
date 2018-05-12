package com.upholstery.share.battery.mvp.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.fragment.BaseFragment
import cn.zcoder.xxp.base.mvp.ui.fragment.BaseMvpFragment
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CouponResponse
import com.upholstery.share.battery.mvp.modle.entity.InvitesResponse
import com.upholstery.share.battery.mvp.presenter.CouponPresenter
import kotlinx.android.synthetic.main.fragment_coupon.*
import java.text.SimpleDateFormat


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description :优惠券页面
 */

class CouponFragment : BaseMvpFragment<MvpView, CouponPresenter>(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mAdapter: BaseQuickAdapter<CouponResponse.DataBean, BaseViewHolder>
    override fun showLoading(type: Int) {
        mSwipeRefreshCoupon.post {
            mSwipeRefreshCoupon.isRefreshing = true

        }
    }

    override fun dismissLoading(type: Int) {
        mSwipeRefreshCoupon.isRefreshing = false
    }

    override fun handlerError(type: Int, e: String) {
        showSnackBar(R.string.load_error, R.string.retry) { onRefresh() }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        if ((data as CouponResponse).data.isEmpty()) {
            showSnackBar(R.string.no_data, Snackbar.LENGTH_LONG)
        }
        mAdapter.replaceData(data.data)
    }

    override fun createPresenter(): CouponPresenter = CouponPresenter()


    override fun onRefresh() {
        getPresenter().getCoupon(COUPON_TYPE[arguments.getInt("page")])

    }

    /**
     * 根據頁 index  得到優惠卷的類型
     */
    private val COUPON_TYPE = intArrayOf(3, 0, 2)

    override fun getLayoutId(): Int {
        return R.layout.fragment_coupon

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mSwipeRefreshCoupon.setOnRefreshListener(this)
        initRecyclerView()

    }

    override fun lazyLoad() {
        super.lazyLoad()
        onRefresh()
    }

    private fun initRecyclerView() {
        mRvCoupon.layoutManager = LinearLayoutManager(activity.applicationContext)
        mAdapter = object : BaseQuickAdapter<CouponResponse.DataBean, BaseViewHolder>(R.layout.recycler_coupon) {
            override fun convert(helper: BaseViewHolder, item: CouponResponse.DataBean) {
                helper.setText(R.id.tvCount,"${item.worthMoney/100}")
                        .setText(R.id.tvTime, TimeUtils.millis2String(item.endTime, SimpleDateFormat(" yyyy-MM-dd")))

            }

        }
        mRvCoupon.adapter = mAdapter

    }

    companion object {
        fun newInstance(page: Int): CouponFragment {
            val fragment = CouponFragment()
            val bundle = Bundle()
            bundle.putInt("page", page)
            fragment.arguments = bundle
            return fragment
        }
    }
}