package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.RegisterPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.VerPhoneNumberDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description :  注册页面
 */

class RegisterActivity : BaseMvpActivity<MvpView, RegisterPresenter>(), View.OnClickListener {
    private val mWarningDialog by lazy {
        WarningDialog.newInstance(getString(R.string.whether_to_start_dialup_validation),
                getString(R.string.cancel), getString(R.string.confirm))
    }
    private val mVerPhoneNum by lazy {
        VerPhoneNumberDialog.newInstance()
    }

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
            0x11 -> toast(R.string.the_required_field_cannot_be_empty)//必填字段為空的情況
            0x14 -> toast(e)
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x11 -> {
                checkPermissionByCall()
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
    private val REQUEST_CODE_TO_SETTING_PWD_QUESTION = 0x12
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
        mBtnVer.onClick(this)
        mTvSettingPwdQuestion.onClick(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> onBackPressed()
            R.id.mTvCodeName -> startActivityForResult(Intent(this,
                    SelectAreaCodeActivity::class.java), REQUEST_CODE)
            R.id.mBtnGetVerCode -> getPresenter().getVerCode(
                    mTxtPhoneNum.text.toString(), "1", 0x13)
            R.id.mBtnVer -> {//撥號驗證
                val phoneNum = mTxtPhoneNum.text.toString()
                if (phoneNum.isEmpty()) {
                    toast(R.string.phone_num_not_be_null)
                    return
                }
                mVerPhoneNum.setListener({
                }, {
                    getPresenter().getVerCode(phoneNum, "2", 0x11)
                })
                mVerPhoneNum.setData("($phoneNum)")
                showDialog(mVerPhoneNum)

            }
            R.id.mTvSettingPwdQuestion -> {
                //去設置密保問題的界面
                startActivityForResult<PwdQuestionActivity>(REQUEST_CODE_TO_SETTING_PWD_QUESTION)
            }
            R.id.mTvToLogin -> onBackPressed()
            R.id.mBtnRegister -> {
                if (!mCbAgree.isChecked) {
                    toast(R.string.please_agree_relevant_protocol)
                    return
                }
                getPresenter().register(
                        mTxtPhoneNum.text.toString(),
                        mTxtFirstName.text.toString(),
                        mTxtLastName.text.toString(),
                        mTxtInvitationCode.text.toString(),
                        mPwdQuestion,0x14)
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

    private var mPwdQuestion = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                mTvCodeName.text = "+${it.getStringExtra("areaCode")}"
            }
        } else if (requestCode == REQUEST_CODE_TO_SETTING_PWD_QUESTION && resultCode == Activity.RESULT_OK) {
            data?.let {

                mPwdQuestion = "${it.getStringExtra("month")}${it.getStringExtra("day")}"
            }
        }

    }


}