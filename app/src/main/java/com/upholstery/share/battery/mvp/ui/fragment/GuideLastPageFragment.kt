package com.upholstery.share.battery.mvp.ui.fragment

import android.support.v4.app.Fragment
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.fragment.BaseFragment
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.activity.LoginActivity
import com.upholstery.share.battery.mvp.ui.activity.RegisterActivity
import kotlinx.android.synthetic.main.fragment_guide_last_page.*
import org.jetbrains.anko.support.v4.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description :
 */

class GuideLastPageFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_guide_last_page
    }

    override fun bindListener() {
        super.bindListener()
        mBtnRegister.onClick { toRegister() }
        mBtnLogin.onClick { toLogin() }
    }

    private fun toLogin() {
        activity.finish()
        startActivity<LoginActivity>()
    }

    private fun toRegister() {
        activity.finish()
        startActivity<RegisterActivity>()

    }

    companion object {
        fun newInstance(): Fragment {
            return GuideLastPageFragment()
        }
    }
}