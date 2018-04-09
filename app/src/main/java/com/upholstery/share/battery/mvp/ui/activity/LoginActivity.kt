package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import cn.zcoder.xxp.base.ext.dismissDialog
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.LoginPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description :
 */

class LoginActivity : BaseMvpActivity<MvpView, LoginPresenter>(),
        View.OnClickListener {
    private val REQUEST_CODE = 0x11
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.phone_is_disabled)
            }
            0x11 -> {
                mBtnGetVerCode.stop()
                toast(R.string.ver_code_send_error)
            }
            0x12 -> {
                toast(e)
            }
            0x13 -> toast(R.string.ver_code_is_empty)
            0x15 -> toast(R.string.phone_is_disabled)
            else -> {
            }
        }

    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x11 -> {
                mBtnGetVerCode.start()
                toast(R.string.ver_code_send_success)
            }
            0x12 -> {
                toast(R.string.login_success)
                startActivity<MainActivity>()
                finish()
            }
            else -> {
            }
        }
    }


    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        dismissDialog(mLoadingDialog)
    }

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mBntRegister -> {
                startActivity<RegisterActivity>()
            }
            R.id.mBtnGetVerCode -> {
                getPresenter().getVerCode(mTvAreaCode.text.toString(),mEtPhone.text.toString(),  "2", 0x11)
            }
            R.id.mBtnLogin -> {
                getPresenter().login(mTvAreaCode.text.toString(),mEtPhone.text.toString(), mEtVerCode.text.toString(), 0x12)
            }
            R.id.mIvToFacebookLogin -> {

            }
            R.id.mIvToGoogleLogin -> {

            }
            R.id.mRlSelectArea -> startActivityForResult(Intent(this,
                    SelectAreaCodeActivity::class.java),REQUEST_CODE)
            else -> {
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun bindListener() {
        super.bindListener()
        mBntRegister.onClick(this)
        mBtnGetVerCode.onClick(this)
        mBtnLogin.onClick(this)
        mIvToFacebookLogin.onClick(this)
        mIvToGoogleLogin.onClick(this)
        mRlSelectArea.onClick(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE&&resultCode==Activity.RESULT_OK){
            data?.let {
                mTvAreaName.text =it.getStringExtra("areaName")
                mTvAreaCode.text ="+${it.getStringExtra("areaCode")}"
            }
            }

    }
}