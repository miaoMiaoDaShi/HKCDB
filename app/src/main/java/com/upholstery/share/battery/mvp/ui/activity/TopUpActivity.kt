package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.dialog.SelectPayTypeToTopUpPop
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_top_up.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description :
 */

class TopUpActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mBtnTopUp -> {
                if (mCbAgree.isChecked) {
                    popTopUpWindow(v)
                } else{
                    toast(R.string.please_agree_relevant_protocol)
                }
            }
            else -> {
            }
        }
    }

    override fun bindListener() {
        super.bindListener()
        mBtnTopUp.setOnClickListener(this)
    }

    private fun popTopUpWindow(v: View) {

        val selectPayTypePop = SelectPayTypeToTopUpPop(2.00, this)
        selectPayTypePop.showAsDropDown(v)
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_top_up
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.topup)
                .setOnLeftImageListener { finish() }
    }
}