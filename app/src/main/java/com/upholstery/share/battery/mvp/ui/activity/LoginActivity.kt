package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import cn.zcoder.xxp.base.ext.dismissDialog
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.LoginPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.VerPhoneNumberDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description : 立即驗證  1.號碼傳給後台,2.打電話
 */

class LoginActivity : BaseMvpActivity<MvpView, LoginPresenter>(),
        View.OnClickListener {
    private val REQUEST_CODE = 0x11
    private val mWarningDialog by lazy {
        WarningDialog.newInstance(getString(R.string.whether_to_start_dialup_validation),
                getString(R.string.cancel), getString(R.string.confirm))
    }
    private val mVerPhoneNum by lazy {
        VerPhoneNumberDialog.newInstance()
    }
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.phone_is_disabled)
            }
            0x11 -> { //手机号验证
                toast(e)
            }
            0x12 -> {
                toast(e)
            }
            0x13 -> {
                toast(R.string.surname_or_realname_not_be_null)
            }
            else -> {
            }
        }

    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x11 -> {
                checkPermissionByCall()
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

    /**
     * 检测权限 并打电话
     */
    fun checkPermissionByCall() {
        RxPermissions(this)
                .request(Manifest.permission.CALL_PHONE)
                .subscribe {
                    if (it) {
                        //取到運營手機號
                        val phoneNum = "15228950262"
                        mWarningDialog.setData("呼叫 $phoneNum?", getString(R.string.cancel), getString(R.string.call))
                        mWarningDialog.setListener({
                        }, {
                            callPhone(phoneNum)
                        })
                        showDialog(mWarningDialog)
                    }
                }
    }

    /**
     * 噠電話的操作
     */
    private fun callPhone(phoneNum: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_CALL
        intent.data = Uri.parse("tel:$phoneNum")
        startActivity(intent)

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
            R.id.mBtnRegister -> {
                startActivity<RegisterActivity>()
            }
            R.id.mBtnVer -> {//撥號驗證
                val phoneNum = mEtPhone.text.toString()
                if (phoneNum.isEmpty()) {
                    toast(R.string.phone_num_not_be_null)
                    return
                }
                mVerPhoneNum.setListener({
                }, {
                    getPresenter().getVerCode(phoneNum, "2", 0x11)
                })
                mVerPhoneNum.setData(phoneNum)
                showDialog(mVerPhoneNum)

            }
            R.id.mBtnLogin -> {
                getPresenter().login(
                        mEtPhone.text.toString(),
                        mEtSurname.text.toString(),
                        mEtRealname.text.toString().trim(),
                        0x12)
            }
            R.id.mIvToFacebookLogin -> {

            }
            R.id.mIvToGoogleLogin -> {

            }
            R.id.mRlSelectArea -> startActivityForResult(Intent(this,
                    SelectAreaCodeActivity::class.java), REQUEST_CODE)
            R.id.mBtnForgetPwd->{
                //忘記用戶名
            }
            else -> {
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun bindListener() {
        super.bindListener()
        mBtnRegister.onClick(this)
        mBtnForgetPwd.onClick(this)
        mBtnVer.onClick(this)
        mBtnLogin.onClick(this)
        mIvToFacebookLogin.onClick(this)
        mIvToGoogleLogin.onClick(this)
        mRlSelectArea.onClick(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                mTvAreaName.text = it.getStringExtra("areaName")
                mTvAreaCode.text = "+${it.getStringExtra("areaCode")}"
            }
        }

    }
}