package com.upholstery.share.battery.mvp.ui.dialog

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.onClick
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.PayTypeInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.find


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/5
 * Description : 支付的時候選擇支付的type
 */

class SelectPayTypeToPayPop(var money: Long, val point: Int, context: Context) : BasePopupWindow(context) {
    val mDefaultPay by Preference("defaultPayType", 0)

    private var mCurrentMoney = 0L
    /**
     * 支付
     */
    private var mToPay: ((type: Int,money:Long) -> Unit)? = null
    /**
     * 优惠券选择
     */
    private var mToSelectCoupon: (() -> Unit)? = null

    public fun setListener(toPay: (type: Int,money:Long) -> Unit, toSelectCoupon: () -> Unit) {
        mToPay = toPay
        mToSelectCoupon = toSelectCoupon
    }

    /**
     * 选择优惠后调用这个方法  刷新金额
     */
    public fun resetMoney(newMoney: Long) {
        val tvCount = contentView.find<TextView>(R.id.tvCount)
        tvCount.text = String.format(context.getString(R.string.format_pay_money), newMoney / 100.0)

        val btnCount = contentView.find<Button>(R.id.btnCount)
        btnCount.text = String.format(context.getString(R.string.format_pay_btn), newMoney / 100.0)
    }

    /**
     * 减免金额
     */
    public fun reduceMoney(newMoney: Long) {
        mCurrentMoney = mCurrentMoney - newMoney
        resetMoney(mCurrentMoney)
    }

    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pop_pay, null)
        setContentView(contentView)
        contentView.find<ImageView>(R.id.ivClose).onClick {
            dismiss()
        }

        resetMoney(money)
        contentView.find<RelativeLayout>(R.id.rlSelectCoupon).onClick {
            //去選擇優惠券的頁面
            mToSelectCoupon?.let {
                it.invoke()
            }
        }
        contentView.find<CheckBox>(R.id.checkbox).setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                mCurrentMoney = money - point
            } else {
                mCurrentMoney = money
            }

            resetMoney(mCurrentMoney)

        }
        contentView.find<RelativeLayout>(R.id.rlUseCredits).onClick {
            contentView.find<CheckBox>(R.id.checkbox).toggle()
        }


        val rvPayType = contentView.find<RecyclerView>(R.id.mRvPayType)
        var payTypes = mutableListOf(PayTypeInfo(0, context.getString(R.string.pay_alipay), R.drawable.ic_pay_alipay, false),
                PayTypeInfo(1, context.getString(R.string.pay_we_char), R.drawable.ic_pay_wechar, false),
                PayTypeInfo(2, context.getString(R.string.pay_bank_card), R.drawable.ic_pay_bank_card, false),
                PayTypeInfo(3, context.getString(R.string.pay_wallet), R.drawable.ic_pay_wallet, false))

        payTypes[mDefaultPay].selected = true

        contentView.find<Button>(R.id.btnCount).onClick {
            var payType = 0
            payTypes.forEach {
                if (it.selected) {
                    payType = it.position
                }
            }
            mToPay?.let {
                it.invoke(payType,mCurrentMoney)
            }
        }

        rvPayType.layoutManager = LinearLayoutManager(context)
        val adapter = object : BaseQuickAdapter<PayTypeInfo, BaseViewHolder>(R.layout.recycler_recycle_pay_type, payTypes) {
            override fun convert(helper: BaseViewHolder?, item: PayTypeInfo) {
                helper?.getView<TextView>(R.id.tvPayName)
                        ?.apply {
                            text = item.name
                            setCompoundDrawablesWithIntrinsicBounds(context.resources.getDrawable(item.icon),
                                    null, null, null)
                        }
                helper?.setChecked(R.id.checkbox, item.selected)

            }

        }

        adapter.setOnItemClickListener { adapter, view, position ->
            payTypes.forEach {
                it.selected = false
            }
            payTypes[position].selected = true
            adapter.notifyDataSetChanged()
        }
        rvPayType.adapter = adapter
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

    }


}