package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.TimeUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_birthday.*
import java.text.SimpleDateFormat


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class ChangeBirthdayActivity : BaseActivity() {
    /**
     * 原始的传入的生日字段
     */
    private var mOriginalBirthday = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_birthday
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.birthday)
                .setOnLeftImageListener { finish() }
                .setRightText(getString(R.string.save))


        initTiemPickView()
    }

    private fun save() {
        intent.putExtra("birthday", mTvBirthday.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private lateinit var mPickerView: TimePickerView
    private fun initTiemPickView() {
        mPickerView = TimePickerBuilder(this, { data, view ->
            findViewById<ToolBar>(R.id.mToolBar).showRightText(mTvBirthday.text != mOriginalBirthday)
            mTvBirthday.text = TimeUtils.millis2String(data.time, SimpleDateFormat(" yyyy-MM-dd"))
        })
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.confirm))
                .setTitleText("")
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .build()

    }

    override fun initData() {
        super.initData()
        mOriginalBirthday = intent.getStringExtra("birthday")
        mTvBirthday.text = mOriginalBirthday
    }

    override fun bindListener() {
        super.bindListener()

        mTvBirthday.onClick {
            selectBirthday()
        }
    }

    private fun selectBirthday() {
        mPickerView.show()
    }

    override fun onBackPressed() {
//        if (mOriginalBirthday != mTvBirthday.text.toString()) {
//
//        }
        super.onBackPressed()

    }
}