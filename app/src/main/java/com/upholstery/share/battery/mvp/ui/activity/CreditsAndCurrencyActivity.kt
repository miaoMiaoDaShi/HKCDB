package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.CreditsAndCurrencyPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 信用/货币页面
 */
class CreditsAndCurrencyActivity :BaseMvpActivity<MvpView,CreditsAndCurrencyPresenter>(){
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

    override fun createPresenter(): CreditsAndCurrencyPresenter  = CreditsAndCurrencyPresenter()
}