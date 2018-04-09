package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.DefaultPaySettingInfo
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_recycle.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 付款设置
 */

class DefaultPaySettingActivity : BaseActivity() {
    private lateinit var mPayTypes: MutableList<DefaultPaySettingInfo>


    private var mPayTypeIndex by Preference("defaultPayType", 0)
    override fun getLayoutId(): Int {
        return R.layout.activity_recycle
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.default_pay_type_setting))
                .setOnLeftImageListener { finish() }
                .setOnRightTextListener(getString(R.string.confirm), { done() })
                .showRightText(true)

        mPayTypes = mutableListOf(
                DefaultPaySettingInfo(0, "支付寶"),
                DefaultPaySettingInfo(1, "微信"),
                DefaultPaySettingInfo(2, "信用卡"))


        mPayTypes[mPayTypeIndex].isSelect = true


        mRv.layoutManager = LinearLayoutManager(applicationContext)
        val adapter = object : BaseQuickAdapter<DefaultPaySettingInfo, BaseViewHolder>
        (R.layout.recycler_default_pay, mPayTypes) {
            override fun convert(helper: BaseViewHolder?, item: DefaultPaySettingInfo?) {
                helper?.apply {
                    setText(R.id.mTvPayTypeName, item?.name)
                    setVisible(R.id.mSelect, item?.isSelect!!)
                }

            }

        }
        adapter.setOnItemClickListener { adapter, _, position ->
            mPayTypes.forEach { t: DefaultPaySettingInfo? -> t?.isSelect = false }
            val info = (adapter.data[position] as DefaultPaySettingInfo)
            info.isSelect = !info.isSelect
            adapter.notifyDataSetChanged()

        }
        mRv.adapter = adapter
    }

    private fun done() {
        mPayTypes.forEach { t: DefaultPaySettingInfo? ->
            if (t?.isSelect!!) {
                mPayTypeIndex = t.key
            }
        }
        toast(R.string.setting_success)
        finish()
    }
}