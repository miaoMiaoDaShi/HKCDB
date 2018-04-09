package com.upholstery.share.battery.mvp.ui.dialog

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow

/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/2/11
 * Description :
 */

open class BasePopupWindow(open val context: Context) : PopupWindow() {
    private var mShowAlpha = 0.88f
    private var mBackgroundDrawable: Drawable? = null

    override fun getAnimationStyle(): Int {
        return super.getAnimationStyle()
    }

    init {
        initBasePopupWindow()
    }

    override fun setOutsideTouchable(touchable: Boolean) {
        super.setOutsideTouchable(touchable)
        if (touchable) {
            if (mBackgroundDrawable == null) {
                mBackgroundDrawable = ColorDrawable(0x00000000)
            }
            super.setBackgroundDrawable(mBackgroundDrawable)
        } else {
            super.setBackgroundDrawable(null)
        }
    }

    override fun setBackgroundDrawable(background: Drawable) {
        mBackgroundDrawable = background
        isOutsideTouchable = isOutsideTouchable
    }

    /**
     * 初始化BasePopupWindow的一些信息
     */
    private fun initBasePopupWindow() {
        animationStyle = android.R.style.Animation_Dialog
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = false  //默认设置outside点击无响应
        isFocusable = true
    }

    override fun setContentView(contentView: View?) {
        if (contentView != null) {
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            super.setContentView(contentView)
            addKeyListener(contentView)
        }
    }

    override fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        super.showAtLocation(parent, gravity, x, y)
        val animator = showAnimator()
        animator?.start()
    }

    override fun showAsDropDown(anchor: View) {
        super.showAsDropDown(anchor)
        val animator = showAnimator()
        animator?.start()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        super.showAsDropDown(anchor, xoff, yoff)
        val animator = showAnimator()
        animator?.start()
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val animator = showAnimator()
        animator?.start()
    }

    override fun dismiss() {
        super.dismiss()
        val animator = dismissAnimator()
        animator?.start()
    }

    fun setShowAlpha(alpha: Float) {
        this.mShowAlpha = alpha
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画
     */
    private fun showAnimator(): ValueAnimator? {
        if (mShowAlpha != 1.0f) {
            val animator = ValueAnimator.ofFloat(1.0f, mShowAlpha)
            animator.addUpdateListener { animation ->
                val alpha = animation.animatedValue as Float
                setWindowBackgroundAlpha(alpha)
            }
            animator.duration = 360
            return animator
        } else {
            return null
        }
    }

    /**
     * 窗口隐藏，窗口背景透明度渐变动画
     */
    private fun dismissAnimator(): ValueAnimator? {
        if (mShowAlpha != 1.0f) {
            val animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f)
            animator.addUpdateListener { animation ->
                val alpha = animation.animatedValue as Float
                setWindowBackgroundAlpha(alpha)
            }
            animator.duration = 320
            return animator
        } else {
            return null
        }
    }

    /**
     * 为窗体添加outside点击事件
     */
    private fun addKeyListener(contentView: View?) {
        if (contentView != null) {
            contentView.isFocusable = true
            contentView.isFocusableInTouchMode = true
            contentView.setOnKeyListener(View.OnKeyListener { view, keyCode, event ->
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        dismiss()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
                false
            })
        }
    }

    /**
     * 控制窗口背景的不透明度
     */
    private fun setWindowBackgroundAlpha(alpha: Float) {
        val window = (context as Activity).window
        val layoutParams = window.attributes
        layoutParams.alpha = alpha
        window.attributes = layoutParams
    }
}
