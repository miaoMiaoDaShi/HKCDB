package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.TopUpRuleResponse
import com.upholstery.share.battery.mvp.presenter.PayPresenter
import com.upholstery.share.battery.mvp.ui.dialog.SelectPayTypeToTopUpPop
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_top_up.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description :  充值页面
 */

class TopUpActivity : BaseMvpActivity<MvpView, PayPresenter>(), View.OnClickListener {
    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {
                mTvTopUpRule.text = getString(R.string.loading)
            }
            else -> {
            }
        }

    }

    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> {

            }
            else -> {
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                mTvTopUpRule.text = getString(R.string.load_error)
            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                data as TopUpRuleResponse
                //mTvTopUpRule.text = data.data[0].
                val rules = StringBuffer()
                data.data.forEach {
                    rules.append("充${it.amount / 100}元,送${it.give / 100}元").append("\n")
                }
                mTvTopUpRule.text = rules.toString()
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): PayPresenter = PayPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mBtnTopUp -> {
                if (mCbAgree.isChecked) {
                    popTopUpWindow(v)
                } else {
                    toast(R.string.please_agree_relevant_protocol)
                }
            }
            else -> {
            }
        }
    }

    override fun bindListener() {
        super.bindListener()
        mBtnTopUp.setOnClickListener(this)
    }

    private fun popTopUpWindow(v: View) {

        val selectPayTypePop = SelectPayTypeToTopUpPop(2.00, this)
        selectPayTypePop.showAsDropDown(v)
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_top_up
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.topup)
                .setOnLeftImageListener { finish() }
    }

    override fun initData() {
        super.initData()
        getPresenter().getTopUpRule(0x10)
    }
}