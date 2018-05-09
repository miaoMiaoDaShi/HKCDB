package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import android.widget.LinearLayout
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_web.*
import org.jetbrains.anko.find


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/9
 * Description : 网页页面
 */
class WebActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_web

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(intent.getStringExtra("title"))
                .setOnLeftImageListener { finish() }

        mWebView.loadData(intent.getStringExtra("html"), "text/html", "UTF-8")
    }
}