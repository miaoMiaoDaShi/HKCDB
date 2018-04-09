package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :  更換綁定的手機號碼
 */

class ChangeBindPhoneNumActivity:BaseActivity(),View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_mod_bind_phone
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.change_phone_num))
                .setOnLeftImageListener { finish() }

    }
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}