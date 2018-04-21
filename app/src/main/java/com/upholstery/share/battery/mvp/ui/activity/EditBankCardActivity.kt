package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.BankCardPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_edit_bank_card.*
import org.jetbrains.anko.find

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

            }
            0x11->{//必填字段為空

            }
            0x12->{//添加

            }
            0x13->{//修改

            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {//加載銀行卡詳情

            }
            0x11->{//必填字段為空

            }
            0x12->{//添加

            }
            0x13->{//修改

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
        getPresenter().getBankCardDetail(mId, 0x10)
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
            0 -> {
                //getPresenter().addBankCard()
            }
            1 -> {

            }
            else -> {
            }
        }


    }
}