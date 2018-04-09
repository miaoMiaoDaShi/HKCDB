package com.upholstery.share.battery.mvp.ui.widgets

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.zcoder.xxp.base.ext.onClick

import kotlinx.android.synthetic.main.tool_bar.view.*


/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/7/5
 * Description : 标题栏
 */


class ToolBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {


    private fun initView() {

    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    fun setTitle(title: String): ToolBar {
        mTvTopBarTitle.text = title
        return this
    }
    fun setTitle(title: Int): ToolBar {
        mTvTopBarTitle.text = context.getString(title)
        return this
    }

    /**
     * 设置左边的icon
     *
     * @param id
     * @return
     */
    fun setLefticon(@DrawableRes id: Int): ToolBar {
        mIvTopBarLeft.setImageResource(id)
        return this
    }

    /**
     * 设置右边的图标
     *
     * @param id
     * @return
     */
    fun setRighticon(@DrawableRes id: Int): ToolBar {
        mIvTopBarRight.setImageResource(id)
        return this
    }

    /**
     * 设置右边显示的文字
     *
     * @param title
     * @return
     */
    fun setRightText(title: String): ToolBar {
        mTvTopBarRight.text = title
        return this
    }

    /**
     * 是否显示右边的文字
     *
     * @param isvisible
     * @return
     */
    fun showRightText(isvisible: Boolean): ToolBar {
        mTvTopBarRight.visibility = if (isvisible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 是否显示右边的icon
     *
     * @param isvisible
     * @return
     */
    fun showRightImage(isvisible: Boolean): ToolBar {
        mIvTopBarRight.visibility = if (isvisible) View.VISIBLE else View.GONE
        return this
    }

    /**
     * 是否显示左边的icon
     *
     * @param isvisible
     * @return
     */
    fun showLeftImage(isvisible: Boolean): ToolBar {
        mIvTopBarLeft.visibility = if (isvisible) View.VISIBLE else View.GONE
        return this
    }

    fun setOnLeftImageListener(block: ()->Unit): ToolBar {
        mIvTopBarLeft.onClick(block)
        return this
    }

    fun setOnRightTextListener(text: String, block: ()->Unit): ToolBar {
        mTvTopBarRight.text = text
        mTvTopBarRight.onClick(block)
        return this
    }

    fun setOnRightImageListener(block: ()->Unit): ToolBar {
        mIvTopBarRight.onClick(block)
        return this
    }



}
