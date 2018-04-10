package com.upholstery.share.battery.mvp.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.NearTheSitesResponse
import com.upholstery.share.battery.mvp.ui.activity.NearTheSiteDetailActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/10
 * Description :
 */
class SiteDetailPop(data: NearTheSitesResponse.DataBean, context: Context) : BasePopupWindow(context) {
    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pop_merchant, null)
        contentView.onClick {
            context.startActivity<NearTheSiteDetailActivity>("id" to data.id)
        }

        //头像
        contentView.find<ImageView>(R.id.ivItemMerchantPhoto).load(data.logo)
        //名称
        contentView.find<TextView>(R.id.tvItemMerchantName).text = data.name
        //地址
        contentView.find<TextView>(R.id.tvItemMerchantAddrs).run {
            text = data.address
            isSelected = true
        }
        //可用
        contentView.find<TextView>(R.id.tvCanUse).text =
                String.format(context.getString(R.string.format_can_use, data.empty))
        //可还
        contentView.find<TextView>(R.id.tvRepayable).text =
                String.format(context.getString(R.string.format_repayable, data.empty))

        //距離
        contentView.find<TextView>(R.id.tvLong).text = String.format("%.0f米", data.distance)
        //導航
        contentView.find<LinearLayout>(R.id.llToNav).onClick {
            context.toast("${data.lat} ${data.lng}")
        }

        setContentView(contentView)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
    }

}