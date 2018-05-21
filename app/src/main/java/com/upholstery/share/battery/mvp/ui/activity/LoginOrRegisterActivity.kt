package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import kotlinx.android.synthetic.main.activity_login_or_register.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/21
 * Description : 注册登录
 */
class LoginOrRegisterActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login_or_register
    }

    override fun bindListener() {
        super.bindListener()
        mBtnRegister.onClick { toRegister() }
        mBtnLogin.onClick { toLogin() }
    }

    private fun toLogin() {
        startActivity<LoginActivity>()
    }

    private fun toRegister() {
        startActivity<RegisterActivity>()
    }
}