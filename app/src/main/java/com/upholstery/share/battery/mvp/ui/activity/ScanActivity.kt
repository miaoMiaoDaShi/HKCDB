package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.google.zxing.Result
import com.upholstery.share.battery.mvp.ui.dialog.TipDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import timber.log.Timber


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description :  扫码界面
 *  intent type 0x10 借用
 *              0x11 还
 */

class ScanActivity : BaseActivity(), ZXingScannerView.ResultHandler {
    private val SCAN_PREFIX = "daydaycharge.com?sno="
    override fun handleResult(p0: Result?) {

        //
        p0?.let {
            Timber.e(it.text)
            if (1==1) {
                //val sno = it.text.replace(SCAN_PREFIX, "")
                val urlBlocks = it.text.split("sno=")
                if(urlBlocks.size!=2){
                    TipDialog.show(it.text,supportFragmentManager)
                    return
                }
                val sno = urlBlocks[1]


                when (intent.getIntExtra("type", 0)) {
                //借用
                    0x10 -> {
                        startActivity<BorrowingActivity>("sno" to sno)
                    }
                //退还
                    0x11 -> {
                        startActivity<ReturnPowerBankActivity>("sno" to sno)
                    }
                    else -> {
                    }
                }
                finish()
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_scan
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.scan_scan)
                .setOnLeftImageListener { finish() }
        mScannerView.setResultHandler(this)
        mScannerView.setAutoFocus(true)
    }


    override fun onResume() {
        super.onResume()
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }
}