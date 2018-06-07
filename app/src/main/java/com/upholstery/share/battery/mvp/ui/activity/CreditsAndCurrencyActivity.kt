package com.upholstery.share.battery.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CreditAndCurrencyResponse
import com.upholstery.share.battery.mvp.modle.entity.CreditCommodityResponse
import com.upholstery.share.battery.mvp.presenter.CreditsAndCurrencyPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_credits_and_currency.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 信用/货币页面
 */
class CreditsAndCurrencyActivity : BaseMvpActivity<MvpView, CreditsAndCurrencyPresenter>(), BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    /**
     * 加载等待框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_credits_and_currency
    }

    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {
                showDialog(mLoadingDialog)
            }
            0x11 -> {

            }
            else -> {
            }
        }

    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> {
                cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
            }
            0x11 -> {
                mSwipeRefresh.isRefreshing = false
            }
            0x12 -> {

            }
            else -> {
            }
        }

    }

    override fun handlerError(type: Int, e: String) {
        toast(e)
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                data as CreditAndCurrencyResponse
                mTvCreditCount.text = "${data.data.trustPoint}"
                mTvCurrencyCount.text = "${data.data.point}"
            }
            0x11 -> {
                data as CreditCommodityResponse

                mAdapter.replaceData(data.data)
                if (data.data.isEmpty()) {
                    mAdapter.loadMoreEnd()
                }
            }
            0x12 -> {
                data as CreditCommodityResponse
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
                .setTitle(R.string.day_day_trust)
                .setOnLeftImageListener { finish() }
        mSwipeRefresh.setOnRefreshListener(this)
        initRecyclerView()

    }

    private lateinit var mAdapter: BaseQuickAdapter<CreditCommodityResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
        //mRv.layoutManager = GridLayoutManager(applicationContext, 2)
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<CreditCommodityResponse.DataBean, BaseViewHolder>
        (R.layout.recycler_credits_commodity) {
            override fun convert(helper: BaseViewHolder, item: CreditCommodityResponse.DataBean) {
                helper.setText(R.id.mTvCommodityName, item.name)
                        .setText(R.id.mTvCreditCount, "${item.point} ${getString(R.string.credits)}")
                        .getView<ImageView>(R.id.mIvCommodityImage).load(item.image?:R.drawable.ic_image_error)
            }

        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                //去商品详情页面  避免head页面被单击
                val data = adapter.data[position] as CreditCommodityResponse.DataBean
                if (data.id == 0) {
                    toast("非法ID值")
                    return@run
                }
                startActivity<ConversionCommodityDetailActivity>("id" to data.id)
            }
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener(this, mRv)
        mRv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildLayoutPosition(view) + 1
                if (position % 2 == 0 && position != 0) {//右边
                    outRect.top = SizeUtils.dp2px(5f)
                    outRect.left = SizeUtils.dp2px(2.5f)
                    outRect.right = SizeUtils.dp2px(5f)
                } else if (position % 2 != 0) {
                    //左边

                    outRect.top = SizeUtils.dp2px(5f)
                    outRect.left = SizeUtils.dp2px(5f)
                    outRect.right = SizeUtils.dp2px(2.5f)
                }
            }
        })
        mRv.adapter = mAdapter

    }

    override fun bindListener() {
        super.bindListener()
        mBtnToCreditsRecord.onClick {
            startActivity<CreditsRecordActivity>()
        }
        mBtnToCurrencyRecord.onClick {
            startActivity<CurrencyRecordActivity>()
        }

    }

    override fun onLoadMoreRequested() {
        getPresenter().getCreditsCommodity(0x12)
    }

    override fun onRefresh() {
        getPresenter().getCreditsCommodity(0x11)
    }

    override fun onResume() {
        super.onResume()
        getPresenter().getCreditAndCurrency(0x10)
        getPresenter().getCreditsCommodity(0x11)
    }

    override fun start() {
        super.start()

    }

    override fun createPresenter(): CreditsAndCurrencyPresenter = CreditsAndCurrencyPresenter()
}