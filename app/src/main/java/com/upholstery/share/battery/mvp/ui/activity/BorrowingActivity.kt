package com.upholstery.share.battery.mvp.ui.activity

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Message
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.ext.visible
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.OpenFreightSpaceResponse
import com.upholstery.share.battery.mvp.modle.entity.UsingOrderResponse
import com.upholstery.share.battery.mvp.presenter.UsePresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_borrowing.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/13
 * Description : 借用中
 */
class BorrowingActivity : BaseMvpActivity<MvpView, UsePresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_borrowing

    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {
                mProBar.visible(true)
            }
            else -> {
            }
        }
    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> {

            }
            0x11 -> {
//                mProBar.visible(false)
//                mLLBorrowingBlock
            }
            else -> {
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(e)
                finish()
            }
            0x11 -> {

            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                mProBar.visible(false)
                mLLBorrowingBlock.visible(true)
                mIvBorrowing.drawable.level = 1
                toQuery()
            }
            0x11 -> {
                data as UsingOrderResponse
                if (data.data.statusX == 1) {
                    getPresenter().openFreightSpace(data.data.sno,0x12)

                }

            }
            0x12->{
                data as OpenFreightSpaceResponse
                toast(R.string.borrow_success)
                startActivity<BorrowRecordDetailActivity>("type" to 0x10, "id" to data.data.id)
                finish()
            }
            else -> {
            }
        }
    }

    /**
     * 查詢是否成功
     */
    private val mValueAnimator = ValueAnimator.ofInt(0, 100)
    private val mDuration = 35000
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)


        }
    }

    private fun toQuery() {

        mValueAnimator.duration = mDuration.toLong()
        mValueAnimator.addUpdateListener {
            val currentPosition = it.animatedValue as Int
            mProgressbarProgress.progress = currentPosition
            mTvBorrowingProgress.text = String.format(getString(R.string.format_position), " $currentPosition")
        }
        mValueAnimator.start()
        mHandler.post(mRunable)

    }

    private val mRunable = object : Runnable {
        override fun run() {
            if (mProgressbarProgress.progress == 100) {
                toast(R.string.borrow_failed)
                startActivity<BorrowFailedActivity>()
                finish()
                return
            }
            getPresenter().getUsingRecord(0x11)
            mHandler.postDelayed(this, 7_000)
        }

    }

    override fun createPresenter(): UsePresenter = UsePresenter()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.borrow_power_bank)
                .setOnLeftImageListener { finish() }
    }

    /**
     * 设备号
     */
    private var mSno = ""

    override fun start() {
        super.start()
        mSno = intent.getStringExtra("sno")
        toBorrow()
    }

    override fun onDestroy() {
        super.onDestroy()
        mValueAnimator.removeAllUpdateListeners()
        mHandler.removeCallbacks(mRunable)
    }

    /**
     * 借用设备
     */
    private fun toBorrow() {
        getPresenter().borrowOne(mSno, "${System.currentTimeMillis()}".substring(0..5), 0x10)

    }
}