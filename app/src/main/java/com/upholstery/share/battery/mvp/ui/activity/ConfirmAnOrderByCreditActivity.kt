package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.formatx
import com.upholstery.share.battery.mvp.modle.entity.CreditCommodityDetailResponse
import com.upholstery.share.battery.mvp.modle.entity.ShippingAddressListResponse
import com.upholstery.share.battery.mvp.presenter.ConfirmAnOrderPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.TipDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_confirm_an_order_by_credit.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import java.util.concurrent.TimeUnit

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 確認訂單(積分頁面)
 */
class ConfirmAnOrderByCreditActivity : BaseMvpActivity<MvpView, ConfirmAnOrderPresenter>(),
        View.OnClickListener {
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_confirm_an_order_by_credit

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x12 -> {
                TipDialog.show(e, supportFragmentManager)
            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                mShippingAddressBean = data as ShippingAddressListResponse.DataBean
                bindDataToShippingAddressView(data)
            }
            0x11 -> {

            }
            0x12 -> {
                //去订单页面
                TipDialog.show(getString(R.string.pay_success), supportFragmentManager)
                Observable.just(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .delay(2, TimeUnit.SECONDS)
                        .subscribe {
                            //区订单页面
                            startActivity<CreditsAndCurrencyActivity>()
                        }
            }
            else -> {
            }
        }
    }

    private fun bindDataToShippingAddressView(data: ShippingAddressListResponse.DataBean) {
        mTvShippingReceiveName.text = data.linkman.formatx(getString(R.string.format_consignee))
        mTvPhoneNum.text = data.phone
        mTvShippingAddress.text = data.address
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.confirm_order)
                .setOnLeftImageListener { finish() }
    }

    private var mCreditCommodityDetail: CreditCommodityDetailResponse.DataBean? = null
    override fun initData() {
        super.initData()
        mCreditCommodityDetail = intent.getSerializableExtra("data") as CreditCommodityDetailResponse.DataBean
        mCreditCommodityDetail?.let {
            mTvCommodityName.text = it.name
            mTvCommodityCountSum.text = "1".formatx(getString(R.string.format_commodity))
            mIvCommodityImage.load(it.image.split(";")[0])
            mTvCreditsCount.text = "${mCreditCommodityDetail?.point!! * mCurrentCommodityCount}"
            mTvCommodityCostSum.text = "${mCreditCommodityDetail?.point!! * mCurrentCommodityCount}"
            //mTvCommodityStandard.text = it.
        }
    }

    override fun bindListener() {
        super.bindListener()
        mIvAdd.onClick(this)
        mRLToSelectShippingAddress.onClick(this)
        mIvReduce.onClick(this)
        mBtnConfirmOrder.onClick(this)
        mRlGoodReceivingTime.onClick(this)
    }

    private var mCurrentCommodityCount = 1
    /**
     * 選擇配送時間
     */
    private val REQUEST_CODE_SELECT_RECEIVING_TIME = 0x12
    /**
     *
     */
    private val REQUEST_CODE_BY_SELECT = 0x10

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mIvAdd -> {
                mCurrentCommodityCount++
                refreshCommodityCount()
            }
            R.id.mIvReduce -> {
                if (mCurrentCommodityCount > 1) {
                    mCurrentCommodityCount--
                }
                refreshCommodityCount()
            }
            R.id.mRLToSelectShippingAddress -> {
                startActivityForResult<SelectShippingAddressActivity>(REQUEST_CODE_BY_SELECT)
            }
            R.id.mBtnConfirmOrder -> {
                mShippingAddressBean?.let {
                    getPresenter().payCommodity(
                            it.linkman,//收货人
                            it.phone,//电话
                            it.province,//省
                            it.city,//市
                            it.area,//区
                            it.address,//详细地址
                            mTvGoodReceivingTime.text.toString(),//配送时间
                            mCurrentCommodityCount,//商品个数
                            "${mCreditCommodityDetail?.id}",//商品id
                            1,//商品类型
                            4,//付款方式
                            0x12
                    )
                }

            }
            R.id.mRlGoodReceivingTime -> {
                startActivityForResult<ReceivingTimeSettingActivity>(REQUEST_CODE_SELECT_RECEIVING_TIME)
            }

            else -> {
            }
        }

    }


    private fun refreshCommodityCount() {
        mTvCommodityCount.text = "$mCurrentCommodityCount"
        mTvCommodityCountSum.text = "$mCurrentCommodityCount".formatx(getString(R.string.format_commodity))
        mTvCreditsCount.text = "${mCreditCommodityDetail?.point!! * mCurrentCommodityCount}"
        mTvCommodityCostSum.text = "${mCreditCommodityDetail?.point!! * mCurrentCommodityCount}"
    }

    override fun start() {
        super.start()
        //加載收貨地址  選取默認的哪一個
        getPresenter().getDefaultShippingAddress(0x10)
    }

    override fun createPresenter(): ConfirmAnOrderPresenter = ConfirmAnOrderPresenter()

    private var mShippingAddressBean: ShippingAddressListResponse.DataBean? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BY_SELECT && resultCode == Activity.RESULT_OK) {
            data?.let {
                mShippingAddressBean = it.getSerializableExtra("data") as ShippingAddressListResponse.DataBean
                mShippingAddressBean?.let {
                    bindDataToShippingAddressView(it)
                }
            }
            /**
             * 選著配送時間段
             */
        } else if (requestCode == REQUEST_CODE_SELECT_RECEIVING_TIME && resultCode == Activity.RESULT_OK) {
            data?.let {
                mTvGoodReceivingTime.text = it.getStringExtra("time")
            }
        }
    }
}