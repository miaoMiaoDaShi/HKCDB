package com.upholstery.share.battery.mvp.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import cn.zcoder.xxp.base.ext.*
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import cn.zcoder.xxp.base.net.RetrofitClient
import com.alipay.sdk.app.PayTask
import com.stripe.android.Stripe
import com.stripe.android.model.Source
import com.stripe.android.model.SourceParams
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.BorrowRecordDetailResponse
import com.upholstery.share.battery.mvp.modle.entity.WalletResponse
import com.upholstery.share.battery.mvp.presenter.BorrowRecordDetailPresenter
import com.upholstery.share.battery.mvp.presenter.UsePresenter
import com.upholstery.share.battery.mvp.presenter.WalletPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.SelectPayTypeToPayPop
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_borrow_record_detail.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import timber.log.Timber


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description : 借用记录  详情页面
 *
 * intent type  0x10 扫码借用成功后
 *              0x11 订单页面  点击进入的
 */

class BorrowRecordDetailActivity : BaseMvpActivity<MvpView, BorrowRecordDetailPresenter>(),
        View.OnClickListener {
    private val SDK_PAY_FLAG = 0x10
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
                        showSnackBar(R.string.pay_success)
                        getPresenter().getBorrowDetail(mId, 0x10)
                    } else {
                        showSnackBar(R.string.pay_failed)
                    }
                }
                else -> {
                }
            }
        }
    }

    private lateinit var mPayPop: SelectPayTypeToPayPop
    private val mUsePresenter by lazy {
        UsePresenter()
    }

    private val mWalletPresenter by lazy {
        WalletPresenter()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvBreakage -> {
                mBorrowRecordDetailResponse?.let {
                    startActivity<BreakageActivity>("sno" to it.data[0].sno,
                            "orderNo" to it.data[0].orderno)
                }
            }
            R.id.mTvReportTheLoss -> {
                mBorrowRecordDetailResponse?.let {
                    mUsePresenter.lostDevice(it.data[0].orderno, 0x11)
                }
            }
            R.id.mBtnToPay -> {
                //查询积分
                mWalletPresenter.getWallet()

            }
            else -> {
            }
        }

    }

    private val mLoadDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    private val mId by lazy {
        intent.getIntExtra("id", 0)
    }

    override fun bindListener() {
        super.bindListener()
        mBtnToPay.onClick(this)
        mTvBreakage.onClick(this)
        mTvReportTheLoss.onClick(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_borrow_record_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0 -> {


            }
            0x10 -> {
                showSnackBar(R.string.load_error, R.string.retry) {
                    getPresenter().getBorrowDetail(mId, type)
                }
            }
            0x11 -> {
                showSnackBar(R.string.commit_failed)
            }
            else -> {
            }
        }

    }

    //订单状态订单状态1-使用中 2-待支付  3-已完成  4-报失 5-报损',
    private val types = arrayOf("使用中", "待支付", "已完成", "报失", "报损")
    private val typeColors = arrayOf(R.color.yellow, R.color.yellow, R.color.yellow, R.color.black,
            R.color.black, R.color.black)

    private var mBorrowRecordDetailResponse: BorrowRecordDetailResponse? = null
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0 -> {
                //查询积分信息后  弹出支付框  it.data[0].cost.toLong()
                data as WalletResponse
                mBorrowRecordDetailResponse?.let {
                    mPayPop = SelectPayTypeToPayPop(1000, data.data.point, this)
                    mPayPop.setListener({ type, money ->
                        when (type) {
                            0 -> {
                                stripePayByAlipay(money.toInt())
                            }
                            1 -> {
                            }
                            2 -> {
                            }
                            else -> {
                            }
                        }

                    }, {

                    })
                    mPayPop.showAtLocation(find(R.id.mBtnToPay),
                            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
                }
            }

            0x10 -> {
                mBorrowRecordDetailResponse = data as BorrowRecordDetailResponse
                if (data.data[0].statusX == 1) {
                    mTvBreakage.visible(true)
                    mTvReportTheLoss.visible(true)
                }
                if (data.data[0].statusX == 2) {
                    mBtnToPay.visible(true)
                }
                val borrowRecordDetail = data.data[0]

                //订单状态
                tvUseStatus.text = types[borrowRecordDetail.statusX - 1]
                tvUseStatus.setTextColor(getColor(typeColors[borrowRecordDetail.statusX - 1]))
                //使用时长
                tvUseTime.text = "${borrowRecordDetail.used}分钟"
                //费用
                tvMoney.text = String.format("%.2f元", borrowRecordDetail.cost / 100.0)
                //订单号
                tvNumber.text = borrowRecordDetail.orderno
                //充电宝id
                tvId.text = borrowRecordDetail.sno
                //地点
                tvName.text = borrowRecordDetail.address

            }
            0x11 -> {
                toast(R.string.breakage_success)
                finish()
            }
            else -> {
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setOnLeftImageListener { finish() }
                .setTitle(R.string.order_detail)
    }

    override fun createPresenter(): BorrowRecordDetailPresenter {
        mUsePresenter.attachView(this)
        mWalletPresenter.attachView(this)
        return BorrowRecordDetailPresenter()
    }

    override fun initData() {
        super.initData()
        getPresenter().getBorrowDetail(mId, 0x10)
    }

    override fun onDestroy() {
        super.onDestroy()
        mUsePresenter.detachView()
        mWalletPresenter.detachView()
    }

    /**
     * stripe.支付(支付宝)
     * @param amount 人民币   分
     */
    private fun stripePayByAlipay(amount: Int) {
        mPayPop.dismiss()
        showDialog(mLoadDialog)
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
                .subscribe({
                    cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
                    invokeAlipayNative(it as Source)
                }, {
                    Timber.e(it)
                    cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
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