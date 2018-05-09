package com.upholstery.share.battery.mvp.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.CompoundButton
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.ScreenUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.ShopingOrderPresenter
import com.upholstery.share.battery.mvp.ui.adapter.FragmentPageAdapter
import com.upholstery.share.battery.mvp.ui.fragment.CouponFragment
import com.upholstery.share.battery.mvp.ui.fragment.ShoppingOrderFragment
import com.upholstery.share.battery.mvp.ui.widgets.ClipPagerTitleView
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_shopping_order.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/6
 * Description : 購物訂單頁面
 */
class ShoppingOrderActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener, ViewPager.OnPageChangeListener {


    override fun getLayoutId(): Int = R.layout.activity_shopping_order

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.shopping_order))
                .setOnLeftImageListener { finish() }


        mVp.addOnPageChangeListener(this)
        mVp.adapter = FragmentPageAdapter(listOf(
                ShoppingOrderFragment.newInstance(0),
                ShoppingOrderFragment.newInstance(1)), supportFragmentManager)
    }

    override fun bindListener() {
        super.bindListener()
        mCbReceiving.isChecked = true
        mCbReceiving.setOnCheckedChangeListener(this)
        mCbWaitEvaluation.setOnCheckedChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                mCbReceiving.isChecked = true
                mCbWaitEvaluation.isChecked = false
            }
            1 -> {
                mCbReceiving.isChecked = false
                mCbWaitEvaluation.isChecked = true
            }
            else -> {
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            when (buttonView.id) {
                R.id.mCbReceiving -> {
                    mCbReceiving.isChecked = true
                    mCbWaitEvaluation.isChecked = false
                    mVp.setCurrentItem(0, true)
                }
                R.id.mCbWaitEvaluation -> {
                    mCbReceiving.isChecked = false
                    mCbWaitEvaluation.isChecked = true
                    mVp.setCurrentItem(1, true)
                }
                else -> {
                }
            }
        }


    }
}