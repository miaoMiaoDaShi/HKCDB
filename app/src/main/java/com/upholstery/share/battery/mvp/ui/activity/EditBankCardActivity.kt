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
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_edit_bank_card.*
import org.jetbrains.anko.find
import java.text.SimpleDateFormat

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/17
 * Description : 添加  编辑 银行卡
 *  传入type  0 增加   1修改
 */
class EditBankCardActivity : BaseMvpActivity<MvpView, BankCardPresenter>() {

    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    /**
     * 操作类型
     */
    private val mType by lazy {
        intent.getIntExtra("type", 0)
    }

    /**
     * 银行卡Id
     */
    private val mId by lazy {
        intent.getStringExtra("id")
    }

    /**
     * 地區名數組
     */
    private val mAreaNames by lazy {
        resources.getStringArray(R.array.area_name)
    }

    override fun getLayoutId(): Int = R.layout.activity_edit_bank_card

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {//加載銀行卡詳情
                toast(R.string.load_bank_detail_failed)
            }
            0x11 -> {//必填字段為空
                toast(R.string.card_holder_name_bank_name_no_not_be_null)
            }
            0x12 -> {//添加
                toast(R.string.save_error)
            }
            0x13 -> {//修改
                toast(R.string.save_error)
            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {//加載銀行卡詳情
                data as BankCardDetailResponse
                mEtName.setText(data.data.bankName)
                mEtBankName.setText(data.data.bankName)
                mEtBankCardNo.setText(data.data.bankNo)
                mEtBankCardValidity.setText(TimeUtils.millis2String(data.data.bankExpire.toLong(),
                        SimpleDateFormat(" yyyy-MM-dd")))
                mBankCardVerCode.setText(data.data.bankCvv2)
            }

            0x12 -> {//添加
                toast(R.string.save_success)
                finish()
            }
            0x13 -> {//修改
                toast(R.string.save_success)
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): BankCardPresenter = BankCardPresenter()
    override fun initData() {
        super.initData()

    }

    override fun start() {
        super.start()
        when (mType) {
            0 -> {

            }
            1->{
                getPresenter().getBankCardDetail(mId, 0x10)
            }
            else -> {
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val mToolbar = find<ToolBar>(R.id.mToolBar)
                .showRightText(true)
                .setOnRightTextListener(getString(R.string.save)) {
                    save()
                }
                .setOnLeftImageListener { finish() }
        when (mType) {
            0 -> {//添加
                mToolbar.setTitle(R.string.add)
            }
            1 -> {//只允许修改地区
                mToolbar.setTitle(R.string.mod)
                mEtName.isEnabled = false
                mEtBankName.isEnabled = false
                mEtBankCardNo.isEnabled = false
                mEtBankCardValidity.isEnabled = false
                mBankCardVerCode.isEnabled = false
            }
            else -> {
            }
        }
    }

    /**
     * 保存
     */
    private fun save() {
        when (mType) {
            0 -> {//添加操作
                getPresenter().addBankCard(mEtName.text.toString(), mEtBankName.text.toString(),
                        mEtBankCardNo.text.toString(), mEtBankCardValidity.text.toString(),
                        mBankCardVerCode.text.toString(), mAreaNames[mSpinnerAreaName.selectedItemPosition], 0x12)
            }
            1 -> {//編輯保存操作
                getPresenter().editBankCard(mId, mAreaNames[mSpinnerAreaName.selectedItemPosition], 0x13)

            }
            else -> {
            }
        }


    }


}