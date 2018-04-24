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

class ChangeEmailAddrsActivity : BaseActivity(), TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        s?.let {
            findViewById<ToolBar>(R.id.mToolBar).showRightText(it.toString() != mOriginalEmail)
        }


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private var mOriginalEmail = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_email_addrs
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.email_addrs)
                .setOnRightTextListener(getString(R.string.done), { save() })
        mTxtEmailAddrs.addTextChangedListener(this)

    }

    override fun initData() {
        super.initData()
        mOriginalEmail = intent.getStringExtra("email")
        mTxtEmailAddrs.setText(mOriginalEmail)
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