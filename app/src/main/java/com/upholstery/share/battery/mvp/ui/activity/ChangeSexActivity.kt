package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_select_sex.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 更改性別
 */

class ChangeSexActivity : BaseActivity(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_select_sex
    }

    override fun onClick(v: View?) {

    }

    override fun bindListener() {
        super.bindListener()
        mTvMan.onClick {
            intent.putExtra("sex", "0")
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
        }
        mTvWoMan.onClick {
            intent.putExtra("sex", "1")
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.sex)
                .setOnLeftImageListener { finish() }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}