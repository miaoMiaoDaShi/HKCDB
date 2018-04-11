package com.upholstery.share.battery.mvp.ui.dialog

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import cn.zcoder.xxp.base.ext.onClick
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import org.jetbrains.anko.find

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/11
 * Description : 联系号码选择
 */
class PhoneNumsPop(phoneNums: List<String>, context: Context) : BasePopupWindow(context) {
    init {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pop_phone_nums, null)
        val mRvPhoneNums = contentView.find<RecyclerView>(R.id.mRvPhoneNums)
        mRvPhoneNums.layoutManager = LinearLayoutManager(context)
        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_phone_nums, phoneNums) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(R.id.mTvPhoneNums, item)

            }
        }

        adapter.setOnItemClickListener { adapter, view, position ->
            mOnItemClickListener?.invoke()
        }

        mRvPhoneNums.adapter = adapter



        contentView.find<TextView>(R.id.mTvClose).onClick {
            dismiss()
        }

        setContentView(contentView)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

    }


    fun setOnItemClickListener(onItemClickListener: () -> Unit) {
        mOnItemClickListener = onItemClickListener
    }

    private var mOnItemClickListener: (() -> Unit)? = null

}