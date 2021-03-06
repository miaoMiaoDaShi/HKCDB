package com.upholstery.share.battery.mvp.ui.dialog

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
 * Description : 充值的時候選擇支付的type
 */

class SelectPayTypeToTopUpPop(money: Float, context: Context) : BasePopupWindow(context) {
    val mDefaultPay by Preference("defaultPayType", 0)
    private var mToAliPay:(()->Unit)? = null
    private var mToWeChatPay:(()->Unit)? = null
    private var mToBankCardPay:(()->Unit)? = null

    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pop_top_up, null)
        contentView.find<ImageView>(R.id.ivClose).onClick {
            dismiss()
        }

        val tvCount = contentView.find<TextView>(R.id.tvCount)
        tvCount.text = String.format(tvCount.text.toString(), money)

        val btnCount = contentView.find<Button>(R.id.btnCount)
        btnCount.text = String.format(btnCount.text.toString(), money)

        val rvPayType = contentView.find<RecyclerView>(R.id.mRvPayType)
        var payTypes = mutableListOf(PayTypeInfo(0,context.getString(R.string.pay_alipay), R.drawable.ic_pay_alipay, false),
                PayTypeInfo(1,context.getString(R.string.pay_we_char), R.drawable.ic_pay_wechar, false),
                PayTypeInfo(2,context.getString(R.string.pay_bank_card), R.drawable.ic_pay_bank_card, false))

        payTypes[mDefaultPay].selected = true

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

        btnCount.onClick {
            var payType = 0
            payTypes.forEach {
                if (it.selected) {
                    payType = it.position
                }
            }

            when (payType) {
                0 -> mToAliPay?.invoke()
                1 -> mToWeChatPay?.invoke()
                2 -> mToBankCardPay?.invoke()
                else -> {
                }
            }
        }
        rvPayType.adapter = adapter
        setContentView(contentView)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

    }

    fun setPayTypeListener(alipay:()->Unit,weChatPay:()->Unit,bankCardPay:()->Unit){
        mToAliPay = alipay
        mToWeChatPay = weChatPay
        mToBankCardPay = bankCardPay
    }

}