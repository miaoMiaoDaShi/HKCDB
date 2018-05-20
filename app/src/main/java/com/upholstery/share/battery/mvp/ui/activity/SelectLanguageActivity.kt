package com.upholstery.share.battery.mvp.ui.activity

import android.content.Intent
import android.os.Build
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import java.util.*

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/20
 * Description : 每次进去选择语言
 */
class SelectLanguageActivity :BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.activity_select_language
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

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}