package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_edit_nickname.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 修改昵称
 */

class EditNicknameActivity : BaseActivity() {
    /**
     * 原始的姓氏
     */
    private var mOriginalLastName = ""
    /**
     * 原始的名字
     */
    private var mOriginalFirstName = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_edit_nickname
    }

    private lateinit var mToobar: ToolBar

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mToobar = findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.edit_nickname))
                .setOnLeftImageListener { finish() }
                .setOnRightTextListener(getString(R.string.save), { save() })
    }

    override fun initData() {
        super.initData()
        mOriginalLastName = intent.getStringExtra("lastName")
        mOriginalFirstName = intent.getStringExtra("firstName")

        mTxtLastName.setText(mOriginalLastName)
        mTxtFirstName.setText(mOriginalFirstName)

        mTxtLastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { mToobar.showRightText(it.toString() != mOriginalLastName) }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        mTxtFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { mToobar.showRightText(it.toString() != mOriginalFirstName) }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun save() {
        if (mTxtLastName.text.isEmpty() || mTxtFirstName.text.isEmpty()) {
            toast(R.string.last_name_or_first_name_not_be_null)
            return
        }
        intent.putExtra("lastName", mTxtLastName.text)
        intent.putExtra("firstName", mTxtFirstName.text)
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }


}