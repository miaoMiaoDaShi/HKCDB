package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.ForgetUserNameResponse
import com.upholstery.share.battery.mvp.presenter.ForgetUserNamePresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_forget_user_name.*

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/6
 * Description : 忘記用戶名
 */
class ForgetUserNameActivity : BaseMvpActivity<MvpView, ForgetUserNamePresenter>() {
    /**
     * 加载等待框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_forget_user_name

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.the_required_field_cannot_be_empty)
            }
            0x11 -> {
                toast(e)
            }
            else -> {
                toast(e)
            }
        }
    }


    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {

            }
            0x11 -> {
                data as ForgetUserNameResponse
                TipDialog.show(String.format(getString(R.string.format_your_username),
                        "${data.data.surname}${data.data.realname}"), supportFragmentManager)
            }
            else -> {
            }
        }
    }

    override fun bindListener() {
        super.bindListener()
        mIvBack.onClick { finish() }
        mBtnConfirm.onClick {
            var month = mEtMonth.text.toString().trim()
            var day = mEtDay.text.toString().trim()
            if (month.isEmpty() || day.isEmpty()) {
                toast(R.string.the_required_field_cannot_be_empty)
                return@onClick
            }
            if (month.toInt() > 12 || day.toInt() > 31) {
                toast(R.string.please_input_standard_input)
                return@onClick
            }
            if (month.length < 2) {
                month = "0$month"
            }
            if (day.length < 2) {
                day = "0$day"
            }
            getPresenter().forgetUserName(mTxtPhoneNum.text.toString().trim(),month+day,0x11)
        }
    }

    override fun createPresenter(): ForgetUserNamePresenter = ForgetUserNamePresenter()
}