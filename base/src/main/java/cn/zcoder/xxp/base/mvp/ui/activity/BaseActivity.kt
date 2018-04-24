package cn.zcoder.xxp.base.mvp.ui.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import com.upholstery.share.base.R
import com.zhy.autolayout.AutoLayoutActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/24
 * Description :
 */

abstract class BaseActivity : AutoLayoutActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = getLayoutId()
        if (layoutId != -1) {
            setContentView(getLayoutId())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setBackgroundDrawableResource(R.color.allActivityColor)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
        }
        initData()
        initView(savedInstanceState)
        bindListener()
        start()

    }

    protected open fun initData() {
    }

    protected open fun start() {

    }

    protected open fun bindListener() {
    }

    /**
     * 加载布局
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected open fun initView(savedInstanceState: Bundle?) {
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}