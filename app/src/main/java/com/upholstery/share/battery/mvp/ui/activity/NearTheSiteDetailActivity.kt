package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.NearTheSiteDetailResponse
import com.upholstery.share.battery.mvp.presenter.NearTheSitePresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_merchant_detail.*
import org.jetbrains.anko.find


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
            getPresenter().getNearTheSiteDetail("${intent.getIntExtra("id", 0)}", 0x10)
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        data as NearTheSiteDetailResponse
        //logo
        mIvLogo.load(data.data[0].logo)
        //名字
        mTvSiteName.text = data.data[0].name
        //地址
        mTvAddrs.run {
            text = data.data[0].address
            isSelected = true
        }
        //電話
        mTvPhone.text = data.data[0].contacts
        //營業時間
        mTvWorkTime.text = data.data[0].openTime
        //形象圖片
        mIvWall.load(data.data[0].wall)
        //可用
        mTvCanUseCount.text = String.format(getString(R.string.format_can_use, data.data[0].canUseCount))
        //可還
        mTvFreeNumCount.text = String.format(getString(R.string.format_repayable, data.data[0].freeNumCount.toInt()))
    }

    override fun createPresenter(): NearTheSitePresenter = NearTheSitePresenter()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.site_detail)
                .setOnLeftImageListener { finish() }


        getPresenter().getNearTheSiteDetail("${intent.getIntExtra("id", 0)}", 0x10)
    }
}