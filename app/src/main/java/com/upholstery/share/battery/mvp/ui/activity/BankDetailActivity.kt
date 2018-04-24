package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.BankCardDetailResponse
import com.upholstery.share.battery.mvp.presenter.BankCardPresenter
import com.upholstery.share.battery.mvp.ui.dialog.BankDetailDialog
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_bank_card_detail.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/22
 * Description : 銀行卡詳情
 */
class BankDetailActivity : BaseMvpActivity<MvpView, BankCardPresenter>() {
    private val mLoadDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_bank_card_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.load_bank_detail_failed)
                finish()
            }
            0x11->{
                toast(R.string.del_failed)
            }
            else -> {
            }
        }
    }

    //0-银联 1-VISA 2-MASTER
    private val mBankCardTypes = arrayOf("銀聯", "VISA", "MASTER")

    private lateinit var mBankCardDetailResponse: BankCardDetailResponse
    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                mBankCardDetailResponse = data as BankCardDetailResponse
                mToolBar.setTitle(mBankCardTypes[data.data.bankType])
                mTvBankCardNo.text = data.data.bankNo
                mTvbankCardTime.text = TimeUtils.millis2String(data.data.bankExpire.toLong(),
                        SimpleDateFormat(" yyyy-MM-dd"))
            }
            0x11->{
                toast(R.string.del_success)
                finish()
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): BankCardPresenter = BankCardPresenter()

    private val mBankDetailDialog by lazy {
        BankDetailDialog()
    }
    private val mToolBar by lazy {
        find<ToolBar>(R.id.mToolBar).setOnLeftImageListener { finish() }
                .showRightImage(true)
                .setRighticon(R.drawable.ic_edit_bank_card)
                .setOnRightImageListener {
                    showDialog(mBankDetailDialog)
                }

    }

    override fun start() {
        super.start()

    }

    override fun onResume() {
        super.onResume()
        getPresenter().getBankCardDetail(intent.getStringExtra("id"), 0x10)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBankDetailDialog.setOnClickListener({
            startActivity<EditBankCardActivity>("id" to intent.getStringExtra("id"))
        }, {
            getPresenter().delBankCard(intent.getStringExtra("id"),0x11)
        })
    }


}