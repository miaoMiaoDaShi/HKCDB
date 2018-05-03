package com.upholstery.share.battery.mvp.ui.activity

import android.annotation.SuppressLint
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.tencent.bugly.crashreport.CrashReport
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.WalletResponse
import com.upholstery.share.battery.mvp.presenter.WalletPresenter
import kotlinx.android.synthetic.main.activity_wallet.*
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 钱包
 */

class MyWalletActivity : BaseMvpActivity<MvpView, WalletPresenter>(), View.OnClickListener {
    override fun showLoading(type: Int) {
        mTvMoney.text = getString(R.string.loading)

    }

    override fun dismissLoading(type: Int) {
    }

    @SuppressLint("SetTextI18n")
    override fun handlerError(type: Int, e: String) {
        toast(R.string.load_wallet_error)
        mTvMoney.text = "0.00"
    }

    override fun handlerSuccess(type: Int, data: Any) {
        mTvMoney.text = String.format("%.2f",(data as WalletResponse).data.money / 100.0)
    }

    override fun createPresenter(): WalletPresenter = WalletPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mBtnTopUp -> {
                startActivity<TopUpActivity>()
            }

            R.id.mRlOrderDetail -> {
                startActivity<OrderListActivity>()
            }
            else -> {
            }
        }

    }

    override fun bindListener() {
        super.bindListener()
        mIvBack.onClick(this)
        mBtnTopUp.onClick(this)
        mRlOrderDetail.onClick(this)

    }

    override fun start() {
        super.start()
    }

    override fun onResume() {
        super.onResume()
        getPresenter().getWallet()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_wallet


    }
}