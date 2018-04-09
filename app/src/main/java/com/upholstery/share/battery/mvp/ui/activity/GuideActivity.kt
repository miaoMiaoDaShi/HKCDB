package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.WindowManager
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.adapter.FragmentPageAdapter
import com.upholstery.share.battery.mvp.ui.fragment.GuideCommonPageFragment
import com.upholstery.share.battery.mvp.ui.fragment.GuideLastPageFragment
import kotlinx.android.synthetic.main.activity_guide_page.*
import java.util.ArrayList


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description : 引导页面
 */

class GuideActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_guide_page
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.setContentView(layoutResID)
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        setup(listOf("http://e.hiphotos.baidu.com/image/pic/item/ac345982b2b7d0a250be39bac7ef76094b369aa5.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/8601a18b87d6277fcdb9b01d24381f30e924fc68.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/7dd98d1001e939014974b01977ec54e736d196a6.jpg"))
    }

    private fun setup(urls: List<String>) {
        var pages: MutableList<Fragment> = ArrayList()
        for (url in urls) {
            pages.add(GuideCommonPageFragment.newInstance(url))
        }
        pages.add(GuideLastPageFragment.newInstance())

        mVp.adapter = FragmentPageAdapter(pages, supportFragmentManager)
    }


}