package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.PwdQuestionPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_password_protection.*
import org.jetbrains.anko.find

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/6
 * Description : 密保問題
 */
class PwdQuestionActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_password_protection
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
//        find<ToolBar>(R.id.mToolBar)
//                .setTitle(R.string.setting_pwd_question)
//                .setOnRightImageListener { finish() }
    }

    override fun bindListener() {
        super.bindListener()
        mIvBack.onClick {
            finish()
        }
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
            val intent = Intent()
            intent.putExtra("month", month)
            intent.putExtra("day", day)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}