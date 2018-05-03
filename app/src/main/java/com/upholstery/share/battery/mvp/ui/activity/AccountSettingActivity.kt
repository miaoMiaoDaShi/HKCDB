package com.upholstery.share.battery.mvp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.upholstery.share.battery.mvp.ui.dialog.TipDialog
import kotlinx.android.synthetic.main.activity_account_setting.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class AccountSettingActivity : BaseActivity(), View.OnClickListener {
    private var mCurrentLanguage by Preference("language", "auto")
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mRlMineData -> {
                startActivity<MineActivity>()

            }
            R.id.mRlDefaultPay -> {
                startActivity<DefaultPaySettingActivity>()
            }
            R.id.mRlCreditCard -> {
                startActivity<BankCardListActivity>()
            }
            R.id.mRlSwitchLanguage -> {
                startActivity<SettingLanguageActivity>()
            }
            R.id.mRlMyCoupon -> {
                startActivity<MyCouponActivity>()
            }
            R.id.mRlCheckUpda -> {
                toast(R.string.check_updateing)
                Handler().postDelayed({ TipDialog.show("已是最新版本~", supportFragmentManager) }, 2000)

            }
            R.id.mBtnLogout -> {
                WarningDialog.show(getString(R.string.logout_tips), getString(R.string.cancel),
                        getString(R.string.confirm), supportFragmentManager)
                        .setListener({ }, {
                            mUserInfo = ""
                            startActivity(intent.setClass(this, LoginActivity::class.java)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                        })
            }
            else -> {
            }
        }

    }

    override fun bindListener() {
        super.bindListener()
        mRlMineData.onClick(this)
        mRlDefaultPay.onClick(this)
        mRlCreditCard.onClick(this)
        mRlMyCoupon.onClick(this)
        mRlSwitchLanguage.onClick(this)
        mRlCheckUpda.onClick(this)
        mBtnLogout.onClick(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_account_setting

    }

    override fun initData() {
        super.initData()
        when (mCurrentLanguage) {
            "auto" -> {
                mTvCurrentLanguage.text = getString(R.string.tracing_system)
            }
            Locale.SIMPLIFIED_CHINESE.language -> {
                mTvCurrentLanguage.text = getString(R.string.simplified_chinese)
            }
            Locale.ENGLISH.language -> {
                mTvCurrentLanguage.text = "English"
            }
            else -> {
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.account_setting)
                .setOnLeftImageListener { finish() }

    }


}