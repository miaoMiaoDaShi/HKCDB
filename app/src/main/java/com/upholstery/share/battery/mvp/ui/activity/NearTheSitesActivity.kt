package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.gms.maps.model.LatLng
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.NearTheSitesResponse
import com.upholstery.share.battery.mvp.presenter.NearTheSitePresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.upholstery.share.battery.utils.MapUtils
import kotlinx.android.synthetic.main.activity_recycle.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description : 商家列表(需要傳入當前的維度  經度  範圍)
 */

class NearTheSitesActivity : BaseMvpActivity<MvpView, NearTheSitePresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    private lateinit var mAdapter: BaseQuickAdapter<NearTheSitesResponse.DataBean, BaseViewHolder>

    override fun onRefresh() {
        getPresenter().getNearTheSites("${intent.getDoubleExtra("lat", 0.0)}",
                "${intent.getDoubleExtra("lng", 0.0)}",
                Constant.LOAD_NEAR_THE_SITES_RANGE, 0x10)
    }

    override fun getLayoutId(): Int = R.layout.activity_recycle

    override fun showLoading(type: Int) {
    }

    override fun dismissLoading(type: Int) {
        mSwipeRefresh.isRefreshing = false
    }

    override fun handlerError(type: Int, e: String) {
        showSnackBar(R.string.load_error, R.string.retry) {
            onRefresh()
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        data as NearTheSitesResponse
        data.data.forEach {
            it.distance = MapUtils
                    .calculateLineDistance(LatLng(intent.getDoubleExtra("lat", 0.0)
                            , intent.getDoubleExtra("lng", 0.0)),
                            LatLng(it.lat, it.lng))
        }

        mAdapter.replaceData(data.data)
    }

    override fun createPresenter(): NearTheSitePresenter = NearTheSitePresenter()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.site_list)
                .setOnLeftImageListener { finish() }
        mSwipeRefresh.setOnRefreshListener(this)

        mSwipeRefresh.post {
            mSwipeRefresh.isRefreshing = true
            onRefresh()
        }

        initRecyclerView()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val bean = adapter.data[position] as NearTheSitesResponse.DataBean
        startActivity<NearTheSiteDetailActivity>("id" to bean.id)

    }

    private fun initRecyclerView() {
        mRv.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = object : BaseQuickAdapter<NearTheSitesResponse.DataBean, BaseViewHolder>(R.layout.recycler_merchant) {
            override fun convert(helper: BaseViewHolder, item: NearTheSitesResponse.DataBean) {
                helper.setText(R.id.tvItemMerchantName, item.name)
                        .setText(R.id.tvItemMerchantAddrs, item.address)
                        .setText(R.id.tvCanUse, String.format(getString(R.string.format_can_use, item.empty)))
                        .setText(R.id.tvRepayable, String.format(getString(R.string.format_repayable, item.empty)))
                        .setText(R.id.tvLong, String.format("%.0f米", item.distance))
                        .getView<ImageView>(R.id.ivItemMerchantPhoto).load(item.logo)

            }

        }
        mAdapter.onItemClickListener = this
        mRv.adapter = mAdapter

    }
}