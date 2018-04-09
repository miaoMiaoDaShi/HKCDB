package com.upholstery.share.battery.mvp.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.mvp.ui.adapter.FragmentPageAdapter
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.fragment.CouponFragment
import com.upholstery.share.battery.mvp.ui.widgets.ClipPagerTitleView
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_my_coupon.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class MyCouponActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_my_coupon
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.coupon))
                .setOnLeftImageListener { finish() }

        val types = listOf<String>(getString(R.string.all), getString(R.string.unused), getString(R.string.out_of_date))
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return types.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = types[index]
                clipPagerTitleView.textColor = Color.WHITE
                clipPagerTitleView.clipColor = Color.BLACK
                clipPagerTitleView.setOnClickListener { mVp.currentItem = index }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight = context.resources.getDimension(R.dimen.common_navigator_height)
                val borderWidth = UIUtil.dip2px(context, 1.0).toFloat()
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.roundRadius = lineHeight / 2
                //indicator.xOffset = context.resources.getDimension(R.dimen.common_navigator_x_offset)
                //indicator.yOffset = borderWidth
                indicator.setColors(resources.getColor(R.color.yellow))
                return indicator
            }
        }
        mIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(mIndicator, mVp)
        mVp.adapter = FragmentPageAdapter(listOf(
                CouponFragment.newInstance(0),
                CouponFragment.newInstance(1),
                CouponFragment.newInstance(2)), supportFragmentManager)
    }
}