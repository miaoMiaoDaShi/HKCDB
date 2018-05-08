package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CreditCommodityDetailResponse
import com.upholstery.share.battery.mvp.modle.entity.ShippingAddressListResponse
import com.upholstery.share.battery.mvp.presenter.ConfirmAnOrderPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_confirm_an_order_by_credit.*
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 確認訂單(積分頁面)
 */
class ConfirmAnOrderByCreditActivity : BaseMvpActivity<MvpView, ConfirmAnOrderPresenter>(),
        View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.activity_confirm_an_order_by_credit

    override fun showLoading(type: Int) {
    }

    override fun dismissLoading(type: Int) {
    }

    override fun handlerError(type: Int, e: String) {
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                data as ShippingAddressListResponse.DataBean
                mEtShippingReceiveName.text = data.linkman.format(getString(R.string.format_consignee))
                mTvPhoneNum.text = data.phone
                mTvShippingAddress.text = data.address
            }
            0x11 -> {

            }
            else -> {
            }
        }
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
            mIvCommodityImage.load(it.image.split(";")[0])
            //mTvCommodityStandard.text = it.
        }
    }

    override fun bindListener() {
        super.bindListener()
        mIvAdd.onClick(this)
        mIvReduce.onClick(this)
    }

    private var mCurrentCommodityCount = 1
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
            else -> {
            }
        }

    }

    private fun refreshCommodityCount() {

        mTvCommodityCount.text = "$mCurrentCommodityCount"
    }

    override fun start() {
        super.start()
        //加載收貨地址  選取默認的哪一個
        getPresenter().getDefaultShippingAddress(0x10)
    }

    override fun createPresenter(): ConfirmAnOrderPresenter = ConfirmAnOrderPresenter()
}