package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.TextView
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.ImageLoader
import com.upholstery.share.battery.mvp.presenter.ShopPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.youth.banner.Banner
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 商城主页面
 */
class ShopActivity : BaseMvpActivity<MvpView, ShopPresenter>(), ViewPager.OnPageChangeListener {


    /**
     * 加载等待框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_shop

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handlerSuccess(type: Int, data: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.shop))
                .setOnLeftImageListener { finish() }
        setupHeaderView(mutableListOf(
                "https://camo.githubusercontent.com/72e7034bb9f3ed5e4d28c25a94adb22bb9e7cb98/687474703a2f2f6f63656835316b6b752e626b742e636c6f7564646e2e636f6d2f62616e6e65725f6578616d706c65312e706e67",
                "https://camo.githubusercontent.com/72e7034bb9f3ed5e4d28c25a94adb22bb9e7cb98/687474703a2f2f6f63656835316b6b752e626b742e636c6f7564646e2e636f6d2f62616e6e65725f6578616d706c65312e706e67",
                "https://camo.githubusercontent.com/72e7034bb9f3ed5e4d28c25a94adb22bb9e7cb98/687474703a2f2f6f63656835316b6b752e626b742e636c6f7564646e2e636f6d2f62616e6e65725f6578616d706c65312e706e67"))
    }

    /**
     * 頭佈局裝載
     */
    private lateinit var mTvIndicator:TextView
    private var mBannerCount = 0
    private fun setupHeaderView(images: List<String>) {
        val headerContentView = layoutInflater.inflate(R.layout.header_shop_main, null)
        val banner = headerContentView.find<Banner>(R.id.mBanner)
        mTvIndicator = headerContentView.find(R.id.mTvIndicator)
        banner.setImageLoader(ImageLoader())
        banner.setOnPageChangeListener(this)
        banner.setImages(images)
        banner.start()

    }

    override fun createPresenter(): ShopPresenter = ShopPresenter()

    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mTvIndicator.text = "$position/$mBannerCount"
    }
}