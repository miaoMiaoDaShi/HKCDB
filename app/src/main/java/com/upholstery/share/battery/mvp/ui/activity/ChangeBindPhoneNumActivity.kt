package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.fromJson
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.UserResponse
import com.upholstery.share.battery.mvp.presenter.VerPhonePresenter
import com.upholstery.share.battery.mvp.presenter.ModPersonalDataPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_mod_bind_phone.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :  更換綁定的手機號碼
 */

class ChangeBindPhoneNumActivity : BaseMvpActivity<MvpView, ModPersonalDataPresenter>(), View.OnClickListener {
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
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
            0x11 -> {
                mBtnGetVerCode.stop()
                toast(R.string.ver_code_send_error)
            }
            0x12 -> {
                toast(e)
            }
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
                toast(R.string.mod_success)
                finish()
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): ModPersonalDataPresenter = ModPersonalDataPresenter()

    private val REQUEST_CODE = 0x11
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mod_bind_phone
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.change_phone_num))
                .setOnLeftImageListener { finish() }

        mGetVerCodePresenter.attachView(this)

    }

    private val mGetVerCodePresenter by lazy {
        VerPhonePresenter<MvpView>()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvAreaCode -> {
                startActivityForResult<SelectAreaCodeActivity>(REQUEST_CODE)
            }
            R.id.mBtnGetVerCode -> {
                mGetVerCodePresenter.getVerCode(mTvAreaCode.text.toString().replace("+", ""),
                        mEtPhone.text.toString(), "4", 0x11)
            }
            R.id.mBtnCommit -> {
                fromJson<UserResponse>(mUserInfo)?.let {

                    getPresenter().modBindPhone(mTvAreaCode.text.toString().replace("+", ""),
                            mEtPhone.text.toString(),
                            mEtVerCode.text.toString(), "${it.data.uid}", 0x12)
                }
            }
            else -> {
            }
        }
    }

    override fun bindListener() {
        super.bindListener()
        mTvAreaCode.onClick(this)
        mBtnGetVerCode.onClick(this)
        mBtnCommit.onClick(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mGetVerCodePresenter.detachView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                mTvAreaCode.text = "+${it.getStringExtra("areaCode")}"
            }
        }

    }

}