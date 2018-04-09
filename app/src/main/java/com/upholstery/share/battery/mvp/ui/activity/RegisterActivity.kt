package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.RegisterPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_register.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description :  注册页面
 */

class RegisterActivity : BaseMvpActivity<MvpView, RegisterPresenter>(), View.OnClickListener {
    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)

    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.phone_is_disabled)
            }
            0x11 -> toast(R.string.the_required_field_cannot_be_empty)
            0x13 -> {
                mBtnGetVerCode.stop()
                toast(R.string.ver_code_send_error)
            }
            0x14 -> toast(e)
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x13 -> {
                mBtnGetVerCode.start()
                toast(R.string.ver_code_send_success)
            }
            0x14 -> {
                toast(R.string.register_success)
                onBackPressed()
            }
            else -> {
            }
        }

    }

    override fun createPresenter(): RegisterPresenter = RegisterPresenter()


    private val REQUEST_CODE = 0x11
    /**
     * 对话框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_register
    }

    override fun bindListener() {
        super.bindListener()
        mIvBack.onClick(this)
        mTvCodeName.onClick(this)
        mBtnGetVerCode.onClick(this)
        mBtnRegister.onClick(this)
        mTvToLogin.onClick(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> onBackPressed()
            R.id.mTvCodeName -> startActivityForResult(Intent(this,
                    SelectAreaCodeActivity::class.java), REQUEST_CODE)
            R.id.mBtnGetVerCode -> getPresenter().getVerCode(mTvCodeName.text.toString(),
                    mTxtPhoneNum.text.toString(), "1", 0x13)
            R.id.mTvToLogin -> onBackPressed()
            R.id.mBtnRegister -> getPresenter().register(mTvCodeName.text.toString(),
                    mTxtPhoneNum.text.toString(), mTxtVerificationCode.text.toString(),
                    mTxtFirstName.text.toString(), mTxtLastName.text.toString(),
                    mTxtEmail.text.toString(), mTxtInvitationCode.text.toString(), 0x14)
            else -> {
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                mTvCodeName.text = "+${it.getStringExtra("areaCode")}"
            }
        }

    }

}