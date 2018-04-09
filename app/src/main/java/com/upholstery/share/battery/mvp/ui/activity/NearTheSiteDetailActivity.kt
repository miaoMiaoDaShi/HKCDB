package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.NearTheSitePresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description : 網點列表詳情頁面
 */

class NearTheSiteDetailActivity : BaseMvpActivity<MvpView, NearTheSitePresenter>() {

    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_merchant_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        showSnackBar(R.string.load_error, R.string.retry) {
            getPresenter().getNearTheSiteDetail(intent.getStringExtra("id"), 0x10)
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {

    }

    override fun createPresenter(): NearTheSitePresenter = NearTheSitePresenter()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)


    }
}