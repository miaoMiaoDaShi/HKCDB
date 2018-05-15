package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_email_addrs.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class ChangeEmailAddrsActivity : BaseActivity() {



    override fun getLayoutId(): Int {
        return R.layout.activity_email_addrs
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.email_addrs)
                .showRightText(true)
                .setOnRightTextListener(getString(R.string.done), { save() })

    }

    override fun initData() {
        super.initData()
        mTxtEmailAddrs.setText(intent.getStringExtra("email"))
    }

    private fun save() {

        intent.putExtra("email", mTxtEmailAddrs.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}