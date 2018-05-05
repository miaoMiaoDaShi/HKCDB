package com.upholstery.share.battery.mvp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.upholstery.share.battery.R
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 商品列表頁排序
 */
class CommodityCategoryListPop(val currentSortType: Int, context: Context)
    : BasePopupWindow(context) {
    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pop_commodity_list_sort, null)
        val mTvAscendingSort = contentView.find<TextView>(R.id.mTvAscendingSort)
        val mTvDescendingSort = contentView.find<TextView>(R.id.mTvDescendingSort)
        when (currentSortType) {
            0 -> {
                mTvAscendingSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_select, 0)
            }
            1 -> {
                mTvDescendingSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_select, 0)
            }
            else -> {
            }
        }
        setContentView(contentView)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}