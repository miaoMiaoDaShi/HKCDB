package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.formatx
import com.upholstery.share.battery.event.ShoppingOrderRefreshEvent
import com.upholstery.share.battery.mvp.modle.entity.CommodityOrderListResponse
import com.upholstery.share.battery.mvp.presenter.ShopingOrderPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.dialog.TipDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningTipsDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_shopping_order_detail_by_credit.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/10
 * Description :  商城 订单页面(積分商品訂單详情页)  页面的button有两种情况,依据type字段
 *
 *  //出现的数据加载情况   目前只有确认收货
//评价订单为二级页面
 */
class ShoppingOrderDetailByCreditActivity : BaseMvpActivity<MvpView, ShopingOrderPresenter>() {
    private val mWarningTipsDialog by lazy {
        WarningTipsDialog.newInstance(getString(R.string.confirm_receiving_success), getString(R.string.evaluation_order))
    }
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    /**
     * 页面的显示类型
     * 0 积分确认收货
     * 1 积分待评价状态
     *
     */
    private var mType :Int = 0

    private val mData by lazy {
        intent.getSerializableExtra("data") as CommodityOrderListResponse.DataBean
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.shopping_order_detail)
                .setOnLeftImageListener { finish() }

        mType =  intent.getIntExtra("type", 0)
        setupUiByType(mType)

        //收貨人
        mTvShippingReceiveName.text = mData.consignee.formatx(getString(R.string.format_consignee))
        //電話號碼
        mTvPhoneNum.text = mData.linkPhone
        //收貨地址
        mTvShippingAddress.text = mData.province + mData.city + mData.area + mData.address
        //商品圖片
        mIvCommodityImage.load(mData.image)
        //商品名字
        mTvCommodityName.text = mData.name
        //商品數量
        mTvCommodityCount.text = "${mData.number}"
        //商品積分
        mTvCommodityCost.text = "${mData.point}"

        //商品總價
        mTvCommodityCostSum.text = "${mData.point.toInt() * mData.number}"
        //配送時間
        mTvGoodReceivingTime.text = "6點-8點".formatx(getString(R.string.format_good_receiving_time))
        //配送方式
        mTvShipmentsType.text = resources.getStringArray(R.array.delivery_type)[0].formatx(getString(R.string.format_shipments_type))

        //訂單編號
        mTvToShoppingOrderNo.text = mData.orderNo.formatx(getString(R.string.format_shopping_order_no))
        //下單時間
        mTvPayTime.text = TimeUtils.millis2String(mData.createTime, SimpleDateFormat(" yyyy-MM-dd HH:mm:ss"))
                .formatx(getString(R.string.format_pay_time))

        //收貨時間
        mTvReceivingTime.text = TimeUtils.millis2String(mData.createTime, SimpleDateFormat(" yyyy-MM-dd HH:mm:ss"))
                .formatx(getString(R.string.format_pay_time))

    }

    private fun setupUiByType(mType: Int) {
        mBtnConfirm.text = getString(if (mType == 0) R.string.confirm_receiving else R.string.evaluation_order)
    }

    override fun getLayoutId(): Int = R.layout.activity_shopping_order_detail_by_credit

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun bindListener() {
        super.bindListener()
        mBtnConfirm.onClick {
            when (mType) {
                0 -> {//   待確認收貨
                    getPresenter().confirmReceipt(mData.orderNo, 0x10)
                }
                1 -> {//  待評價
                    toEvaluateOrder(mData.orderNo)
                }
                else -> {
                }
            }
        }
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        TipDialog.show(e, supportFragmentManager)
    }

    override fun handlerSuccess(type: Int, data: Any) {
        //确认收货成功,你应该提示用户去评价该
        mType = 1
        setupUiByType(mType)
        mWarningTipsDialog.setListener {
            toEvaluateOrder(mData.orderNo)
        }
        showDialog(mWarningTipsDialog)
        EventBus.getDefault().post(ShoppingOrderRefreshEvent())
    }

    /**
     * 去评价订单的页面
     */
    private fun toEvaluateOrder(orderNo: String) {
        startActivity<EvaluationOfTheOrderActivity>("orderNo" to orderNo)
        finish()
    }


    override fun createPresenter(): ShopingOrderPresenter = ShopingOrderPresenter()
}