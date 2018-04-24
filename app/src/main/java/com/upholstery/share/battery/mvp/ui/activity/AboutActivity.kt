package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_about_us.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class AboutActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mRlCompanyIntroduction -> {
            }
            R.id.mRlContactUs -> {
            }
            else -> {
            }
        }
    }

    override fun bindListener() {
        super.bindListener()
        mRlCompanyIntroduction.onClick(this)
        mRlContactUs.onClick(this)
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_about_us
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.about_us)
                .setOnLeftImageListener { finish() }
    }

}