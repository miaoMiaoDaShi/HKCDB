package com.upholstery.share.battery.mvp.ui.fragment

import android.content.Intent
import android.os.Build
import android.support.v4.app.Fragment
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.fragment.BaseFragment
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.activity.LoginOrRegisterActivity
import kotlinx.android.synthetic.main.fragment_guide_last_page.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*


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
        mTvSelectChinese.onClick {
            changeLanguage(Locale.TAIWAN.language)
        }
        mTvSelectEnglish.onClick {
            changeLanguage(Locale.ENGLISH.language)
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return GuideLastPageFragment()
        }
    }

    /**
     * 语言跟换
     */
    private fun changeLanguage(languageCode: String) {
        //判断是否大于7.0
        var locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales[0]
        } else {
            resources.configuration.locale
        }
        if (languageCode != "auto") locale = Locale(languageCode)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        val intent = Intent(activity, LoginOrRegisterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}