package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.visible
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.UsingOrderResponse
import com.upholstery.share.battery.mvp.presenter.UsePresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_return_power_bank.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/14
 * Description : 退还  请将充电宝插入仓位  退還失敗
 *
 *
 *
 */
class ReturnPowerBankActivity : BaseMvpActivity<MvpView, UsePresenter>() {
    private var mStatus = 0
    override fun showLoading(type: Int) {
        mProBar.visible(true)
        mRlContentBlock.visible(false)
    }

    override fun dismissLoading(type: Int) {

    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10->{//开启好仓位失败
                setViewByFailed()
            }
            0x11 -> {
                setViewByFailed()
            }
            else -> {
            }
        }
    }

    private fun setViewByFailed() {
        mProBar.visible(false)
        mRlContentBlock.visible(true)
        mStatus = 1
        mIvStatus.drawable.level = 0
        mTvHint.text = getString(R.string.return_power_bank_failed)
        mBtnConfirm.text = getText(R.string.twice_scan)
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

        }
    }

    private val mRunable = object : Runnable {
        override fun run() {
            getPresenter().getUsingRecord(0x11)
        }

    }
    /**
     * 查询定订单的次数
     */
    private var mTryCount = 0

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10->{
                mHandler.post(mRunable)
            }
            0x11 -> {
                data as UsingOrderResponse
                /**
                 * 如果不是待支付状态  就继续 查   查5次
                 */
                data.data?.let {
                    if (it.statusX == 1) {
                        if (mTryCount > 5) {
                            setViewByFailed()
                            return
                        }
                        mTryCount++
                        mHandler.postDelayed(mRunable, 5_000)
                    }
                    return
                }


                mProBar.visible(false)
                mRlContentBlock.visible(true)
                mStatus = 0
                mIvStatus.drawable.level = 2
                mTvHint.text = getString(R.string.please_insert_the_charger_into_the_open_position)
                mBtnConfirm.text = getText(R.string.to_see_order)


            }
            else -> {
            }
        }
    }

    override fun createPresenter(): UsePresenter = UsePresenter()

    override fun getLayoutId(): Int = R.layout.activity_return_power_bank

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.return_power_bank)
                .setOnLeftImageListener { finish() }

    }

    override fun initData() {
        super.initData()
        getPresenter().openFreightSpace(intent.getStringExtra("sno"), 0x10)

    }

    override fun bindListener() {
        super.bindListener()
        mBtnConfirm.onClick {
            if (mStatus == 0) {
                toPayOrder()
            } else {
                toTwiceScan()
            }
        }
    }

    /**
     * 在掃一次
     */
    private fun toTwiceScan() {
        startActivity<ScanActivity>("type" to 0x11)
        finish()
    }

    /**
     * 支付訂單
     */
    private fun toPayOrder() {
        startActivity<BorrowRecordActivity>()
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        /**
         * 记得移除Runable
         */
        mHandler.removeCallbacks(mRunable)
    }
}