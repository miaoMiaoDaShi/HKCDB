package com.upholstery.share.battery.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
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
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import com.stripe.android.Stripe
import com.stripe.android.model.SourceParams
import com.upholstery.share.battery.app.Constant
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.alipay.sdk.app.PayTask
import com.stripe.android.exception.CardException
import com.stripe.android.model.Source
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import timber.log.Timber


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description :  充值页面
 */


class TopUpActivity : BaseMvpActivity<MvpView, PayPresenter>(), View.OnClickListener {
    private val SDK_PAY_FLAG = 0x10
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                SDK_PAY_FLAG -> {
                    val answer = msg.obj as Map<String, String>
                    // The result info contains other information about the transaction
                    val resultInfo = answer["result"]
                    val resultStatus = answer["resultStatus"]
                    if (TextUtils.equals(resultStatus, "9000")) {
                        showSnackBar(R.string.top_up_success)
                    } else {
                        showSnackBar(R.string.top_up_failed)
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {
                mTvTopUpRule.text = getString(R.string.loading)
            }
            else -> {
                showDialog(mLoadingDialog)
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
                showSnackBar(e)
            }
        }
    }

    private fun closeKeyboard() {
        val view = window.peekDecorView()
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
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
            0 -> {//微信
                mSelectPayTypePop.dismiss()
            }
            1 -> {//支付寶
                mSelectPayTypePop.dismiss()
                stripePayByAlipay(mMoneyPoint)
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): PayPresenter = PayPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mBtnTopUp -> {
                if (mCbAgree.isChecked && !mEtCount.text.isEmpty()) {
                    closeKeyboard()
                    popTopUpWindow(v)
                } else if (!mCbAgree.isChecked) {
                    toast(R.string.please_agree_relevant_protocol)
                } else {
                    toast(R.string.topup_count_not_be_null)
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

    private var mMoneyPoint = 0
    private lateinit var mSelectPayTypePop:SelectPayTypeToTopUpPop
    private fun popTopUpWindow(v: View) {
//充值金額 元
        val money = mEtCount.text.toString().toFloat()
        mSelectPayTypePop = SelectPayTypeToTopUpPop(money, this)
        val moneyPoint = (money * 100).toInt()
        mMoneyPoint = moneyPoint
        mSelectPayTypePop.setPayTypeListener({ toAliPay(moneyPoint) }, { toWeChatPay(moneyPoint) },
                { bankCardPay(moneyPoint) })
        mSelectPayTypePop.showAsDropDown(v)
    }

    /**
     * 銀行卡  信用卡支付
     */
    private fun bankCardPay(moneyPoint: Int) {


    }

    /**
     * 微信支付
     */
    private fun toWeChatPay(moneyPoint: Int) {

        getPresenter().topUpToWallet(moneyPoint, 0)
    }

    /**
     * 支付寶支付
     */
    private fun toAliPay(moneyPoint: Int) {
        getPresenter().topUpToWallet(moneyPoint, 1)

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

    /**
     * stripe.支付(支付宝)
     * @param amount 人民币   分
     */
    private fun stripePayByAlipay(amount: Int) {
        Observable.create(ObservableOnSubscribe<Source> {
            try {
                val sourceParams = SourceParams
                        .createAlipaySingleUseParams(
                                amount.toLong(),
                                "HKD",
                                Constant.STRIPE_CUSTOMER_NAME,
                                Constant.STRIPE_CUSTOMER_EMAIL,
                                Constant.STRIPE_REDIRECT_ADDRESS
                        )

                val source = Stripe(applicationContext)
                        .createSourceSynchronous(sourceParams, Constant.STRIPE_PUBLISHABLE_KEY)
                it.onNext(source)
            } catch (e: Exception) {
                it.onError(e)
            }
        })
                .compose(RetrofitClient.getDefaultTransformer())
                .doFinally {
                    cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
                }
                .subscribe({
                    invokeAlipayNative(it as Source)
                }, {
                    Timber.e(it)
                    toast(R.string.top_up_failed)
                })
    }


    private fun invokeAlipayNative(source: Source) {
        val alipayParams = source.getSourceTypeData()
        val dataString = alipayParams.get("data_string") as String

        val payRunnable = Runnable {
            // The PayTask class is from the Alipay SDK. Do not run this function
            // on the main thread.
            val alipay = PayTask(this)
            // Invoking this function immediately takes the user to the Alipay
            // app, if in stalled. If not, the user is sent to the browser.
            val result = alipay.payV2(dataString, true)

            // Once you get the result, communicate it back to the main thread
            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        val payThread = Thread(payRunnable)
        payThread.start()
    }
}