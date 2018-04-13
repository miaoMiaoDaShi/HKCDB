package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_borrow_failed.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/13
 * Description :借用失敗
 */
class BorrowFailedActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_borrow_failed

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.borrow_power_bank)
                .setOnLeftImageListener { finish() }
    }

    override fun bindListener() {
        super.bindListener()
        mBtnToScan.onClick {

            startActivity<ScanActivity>("type" to 0x10)
            finish()
        }
    }
}