package com.upholstery.share.battery.mvp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.LanguageInfo
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_recycle.*
import org.jetbrains.anko.find
import android.util.DisplayMetrics
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import org.jetbrains.anko.startActivity
import java.util.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 語言設置
 */

class SettingLanguageActivity : BaseActivity() {
    private var mCurrentLanguage by Preference("language", "auto")
    private val mOriginalLanguage by Preference("language", "auto")
    override fun getLayoutId(): Int {
        return R.layout.activity_recycle
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.language)
                .setOnLeftImageListener { onBackPressed() }
        mSwipeRefresh.isEnabled = false
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        var languages = mutableListOf(
               // LanguageInfo(0, "auto", "自動", false),
                LanguageInfo(1, Locale.TAIWAN.language, "中文", false),
                LanguageInfo(2, Locale.ENGLISH.language, "English", false)
        )
        when (mOriginalLanguage) {
            "auto" -> {
                languages[0].isSelect = true
            }
            Locale.SIMPLIFIED_CHINESE.language -> {
                languages[1].isSelect = true
            }
            Locale.ENGLISH.language -> {
                languages[2].isSelect = true
            }
            else -> {
            }
        }

        val adapter = object : BaseQuickAdapter<LanguageInfo, BaseViewHolder>(R.layout.recycler_default_language,
                languages) {
            override fun convert(helper: BaseViewHolder, item: LanguageInfo) {
                helper.setText(R.id.mTvLanguageName, item.name)
                        .setVisible(R.id.mSelect, item.isSelect)

            }

        }


        /**
         * 切换语言  兼容7.0
         */
        adapter.setOnItemClickListener { adapter, view, position ->

            val languageInfo = adapter.data[position] as LanguageInfo
            mCurrentLanguage = languageInfo.code
            if (!languageInfo.isSelect) {
                (adapter.data as MutableList<LanguageInfo>).forEach {
                    it.isSelect = false
                }
                languageInfo.isSelect = true
                (adapter.data as MutableList<LanguageInfo>).forEach {
                    when (it.position) {
                        0 -> {

                        }
                        1 -> {

                        }
                        else -> {
                        }
                    }
                }

                adapter.notifyDataSetChanged()

            }
        }
        mRv.adapter = adapter

    }

    override fun initData() {
        super.initData()

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

        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (mCurrentLanguage != mOriginalLanguage) {
            val warningDialog = WarningDialog.newInstance(getString(R.string.mod_language_tip),
                    getString(R.string.cancel), getString(R.string.confirm))
            warningDialog.setListener({
                finish()
            }, {
                changeLanguage(mCurrentLanguage)
            })

        } else

        super.onBackPressed()
    }
}