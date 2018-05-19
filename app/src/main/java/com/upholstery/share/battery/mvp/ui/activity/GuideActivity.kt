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

       setup(
               listOf(
                       R.drawable.bg_guide_a,
                       R.drawable.bg_guide_b,
                       R.drawable.bg_guide_c,
                       R.drawable.bg_guide_d,
                       R.drawable.bg_guide_e
               )
       )
    }

//    private fun setup(urls: List<String>) {
//        var pages: MutableList<Fragment> = ArrayList()
//        for (url in urls) {
//            pages.add(GuideCommonPageFragment.newInstance(url))
//        }
//        pages.add(GuideLastPageFragment.newInstance())
//
//        mVp.adapter = FragmentPageAdapter(pages, supportFragmentManager)
//    }
    private fun setup(resIds: List<Int>) {
        var pages: MutableList<Fragment> = ArrayList()
        for (resId in resIds) {
            pages.add(GuideCommonPageFragment.newInstance(resId))
        }
        pages.add(GuideLastPageFragment.newInstance())

        mVp.adapter = FragmentPageAdapter(pages, supportFragmentManager)
    }


}