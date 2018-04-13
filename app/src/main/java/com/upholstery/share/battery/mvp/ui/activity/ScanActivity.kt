package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description :
 */

class ScanActivity : BaseActivity(), ZXingScannerView.ResultHandler {
    private val SCAN_PREFIX = "http://www.xgcdb.global/app/device.html?sno="
    override fun handleResult(p0: Result?) {
        p0?.let {
            if (it.text.contains(SCAN_PREFIX)) {
                val sno = it.text.replace(SCAN_PREFIX, "")
                when (intent.getIntExtra("type", 0)) {
                //借用
                    0x10 -> {
                        startActivity<BorrowingActivity>("sno" to sno)
                    }
                //退还
                    0x11 -> {

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