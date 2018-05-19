package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.app.Constant
import org.jetbrains.anko.startActivity

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
class SplashActivity : BaseActivity() {
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
    private var mIsFirstEnter  = true//by Preference(Constant.KEY_IS_FIRST_ENTER, true)
    override fun getLayoutId(): Int = -1
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun initData() {
        super.initData()
        if (mIsFirstEnter) {
            startActivity<GuideActivity>()
            mIsFirstEnter = false
        } else if (mUserInfo.isEmpty()) {
            startActivity<LoginActivity>()
        } else {
            startActivity<MainActivity>()
        }
        finish()
    }
}