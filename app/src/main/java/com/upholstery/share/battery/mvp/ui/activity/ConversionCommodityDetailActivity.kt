package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CreditCommodityDetailResponse
import com.upholstery.share.battery.mvp.presenter.CreditsAndCurrencyPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningTipsDialog

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description :积分兑换商品详情
 */
class ConversionCommodityDetailActivity : BaseMvpActivity<MvpView, CreditsAndCurrencyPresenter>() {
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

    override fun handlerSuccess(type: Int, data: Any) {
        data as CreditCommodityDetailResponse


    }

    override fun createPresenter(): CreditsAndCurrencyPresenter  = CreditsAndCurrencyPresenter()

    override fun start() {
        super.start()
        val id = intent.getStringExtra("id")
        getPresenter().getCreditCommodityDetail(id, 0x10)
    }

    override fun initData() {
        super.initData()

    }
}