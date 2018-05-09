package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_receiving_setting_time.*
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/9
 * Description : 配送時間設置
 */
class ReceivingTimeSettingActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_receiving_setting_time
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.receiving_time_setting)
                .setOnLeftImageListener { finish() }
                .showRightText(true)
                .setOnRightTextListener(getString(R.string.done)) {
                    val intent = Intent()
                    intent.putExtra("time", mTvGoodReceivingTime.text.toString())
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
    }

    private val mTimes = mutableListOf<String>()
    private val mPickerView by lazy {
        OptionsPickerBuilder(this,
                OnOptionsSelectListener { options1, options2, options3, v ->
                    run {
                        mTvGoodReceivingTime.text = mTimes[options1]
                    }
                })
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.confirm))
                .build<String>()
    }

    override fun bindListener() {
        super.bindListener()
        mRlGoodReceivingTime.onClick {
            mPickerView.show()
        }
    }

    override fun initData() {
        super.initData()
        for (i in 6..21 step 2) {
            mTimes.add("${i}點-${i + 2}點")
        }
        mPickerView.setPicker(mTimes)
    }
}