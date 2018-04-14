package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_contact_us.*
import org.jetbrains.anko.find


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/14
 * Description : 联系我们
 */

class ContactUsActivity :BaseActivity(), View.OnClickListener{


    override fun getLayoutId(): Int  = R.layout.activity_contact_us

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.contact_us)
                .setOnLeftImageListener { finish() }
    }
    override fun bindListener() {
        super.bindListener()
        mRlContactByPhone.onClick(this)
        mRlContactByEmail.onClick(this)
        mRlAboutWebSite.onClick(this)
        mRlContactByWeChat.onClick(this)
        mRlContractByFacebook.onClick(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mRlContactByPhone -> {
            }
            R.id.mRlContactByEmail -> {
            }
            R.id.mRlAboutWebSite -> {
            }
            R.id.mRlContactByWeChat -> {
            }
            R.id.mRlContractByFacebook -> {
            }
            else -> {
            }
        }
    }
}