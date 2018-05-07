package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.JsonBean
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_change_city.*
import java.util.ArrayList


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 修改居住城市
 */

class ChangeCityActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_change_city
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.in_city)
                .setOnRightTextListener(getString(R.string.save), { save() })
    }

    private val options1Items = mutableListOf<JsonBean>()
    private val options2Items = mutableListOf<MutableList<String>>()
    private val options3Items = mutableListOf<MutableList<MutableList<String>>>()
    override fun bindListener() {
        super.bindListener()
        mTvCity.onClick {
            OptionsPickerBuilder(this,
                    OnOptionsSelectListener { options1, options2, options3, v ->
                        {

                        }
                    })
                    .setCancelText(getString(R.string.cancel))
                    .setSubmitText(getString(R.string.confirm))
                    .setLabels(getString(R.string.province), getString(R.string.city), getString(R.string.district))
                    .build<Any>()
                    .setPicker(options1Items as List<Any>?, options2Items as List<MutableList<Any>>?, options3Items as List<MutableList<MutableList<Any>>>?)

        }
    }

    private fun save() {
        val intent = Intent()
        intent.putExtra("city", mTvCity.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}