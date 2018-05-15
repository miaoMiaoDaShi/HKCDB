package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.View
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.ImageLoader
import com.upholstery.share.battery.mvp.modle.entity.CreditCommodityDetailResponse
import com.upholstery.share.battery.mvp.presenter.CreditsAndCurrencyPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningTipsDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.activity_conversion_commodity_detail.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description :积分兑换商品详情
 */
class ConversionCommodityDetailActivity : BaseMvpActivity<MvpView, CreditsAndCurrencyPresenter>(), ViewPager.OnPageChangeListener {
    /**
     * 加载等待框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    private val mWarningDialog by lazy {
        WarningTipsDialog.newInstance(getString(R.string.load_error), getString(R.string.confirm))
    }


    override fun getLayoutId(): Int = R.layout.activity_conversion_commodity_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        mWarningDialog.isCancelable = false
        mWarningDialog.setListener {
            finish()
        }
        showDialog(mWarningDialog)
    }

    private var mCreditCommodityDetailResponse: CreditCommodityDetailResponse? = null
    override fun handlerSuccess(type: Int, data: Any) {
        data as CreditCommodityDetailResponse
        mCreditCommodityDetailResponse = data
        val images = data.data.image.split(";")
        mTvCreditCount.text = "${data.data.point}"
        mTvCommodityName.text = data.data.name
        mTvCommodityDes.text = Html.fromHtml(data.data.details)

        mBannerCount = images.size
        mBanner.setImageLoader(ImageLoader())
        mBanner.setOnPageChangeListener(this)
        mBanner.setImages(images)
        mBanner.start()

    }

    /**
     * 信用分
     */
    private var mBannerCount = 0
    override fun createPresenter(): CreditsAndCurrencyPresenter = CreditsAndCurrencyPresenter()

    override fun start() {
        super.start()
        val id = intent.getIntExtra("id", 0)
        getPresenter().getCreditCommodityDetail(id.toString(), 0x10)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.merchant_detail))
                .setOnLeftImageListener { finish() }
        //initRecyclerViwe()
    }
//
//    private lateinit var mAdapter: BaseQuickAdapter<String, BaseViewHolder>
//    private fun initRecyclerViwe() {
//        mRv.layoutManager = LinearLayoutManager(applicationContext)
//        mAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_commodity_detail) {
//            override fun convert(helper: BaseViewHolder, item: String) {
//                helper.getView<ImageView>(R.id.mIvCommodityImage).load(item)
//            }
//
//        }
//        mRv.adapter = mAdapter
//    }

    override fun bindListener() {
        super.bindListener()
        mTvImmediatelyChange.onClick {
            mCreditCommodityDetailResponse?.let {
                //確認訂單
                startActivity<ConfirmAnOrderByCreditActivity>("data" to it.data)
            }


        }
    }

    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mTvIndicator.text = "${position + 1}/$mBannerCount"
    }

    override fun initData() {
        super.initData()

    }
}