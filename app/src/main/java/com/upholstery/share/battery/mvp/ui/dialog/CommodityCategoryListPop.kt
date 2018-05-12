package com.upholstery.share.battery.mvp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import cn.zcoder.xxp.base.ext.onClick
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
        //升序
        val mTvAscendingSort = contentView.find<TextView>(R.id.mTvAscendingSort)
        //降序
        val mTvDescendingSort = contentView.find<TextView>(R.id.mTvDescendingSort)

        mTvAscendingSort.onClick {
            refreshStatus(mTvAscendingSort,mTvDescendingSort,0)
            dismiss()
        }
        mTvDescendingSort.onClick {
            refreshStatus(mTvAscendingSort,mTvDescendingSort,1)
            dismiss()
        }
       refreshStatus(mTvAscendingSort,mTvDescendingSort,currentSortType)
        setContentView(contentView)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    private fun refreshStatus(mTvAscendingSort: TextView, mTvDescendingSort: TextView,currentSortType:Int) {
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
    }
}