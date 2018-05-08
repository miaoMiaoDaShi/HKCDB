package com.upholstery.share.battery.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.request.target.SizeReadyCallback
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.ImageLoader
import com.upholstery.share.battery.mvp.presenter.ShopPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.activity_shop.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 商城主页面
 */
class ShopActivity : BaseMvpActivity<MvpView, ShopPresenter>(),
        ViewPager.OnPageChangeListener, View.OnClickListener {


    /**
     * 加载等待框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_shop

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
    }

    override fun handlerSuccess(type: Int, data: Any) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.shop))
                .setOnLeftImageListener { finish() }

        initRecyclerView()

    }

    private lateinit var mAdapter: BaseQuickAdapter<String, BaseViewHolder>
    private fun initRecyclerView() {
        mRvShop.layoutManager = GridLayoutManager(applicationContext, 2)
        val commoditys = mutableListOf<String>("大萨达", "大萨达", "大萨达")
        mAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_shop_commodity, commoditys) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(R.id.mTvCommodityName, item)
                        .getView<ImageView>(R.id.mIvCommodityImage).load(R.drawable.bg_shop_commodity_test)
            }

        }
        mAdapter.addHeaderView(
                setupHeaderView(mutableListOf(
                        "https://camo.githubusercontent.com/72e7034bb9f3ed5e4d28c25a94adb22bb9e7cb98/687474703a2f2f6f63656835316b6b752e626b742e636c6f7564646e2e636f6d2f62616e6e65725f6578616d706c65312e706e67",
                        "https://camo.githubusercontent.com/72e7034bb9f3ed5e4d28c25a94adb22bb9e7cb98/687474703a2f2f6f63656835316b6b752e626b742e636c6f7564646e2e636f6d2f62616e6e65725f6578616d706c65312e706e67",
                        "https://camo.githubusercontent.com/72e7034bb9f3ed5e4d28c25a94adb22bb9e7cb98/687474703a2f2f6f63656835316b6b752e626b742e636c6f7564646e2e636f6d2f62616e6e65725f6578616d706c65312e706e67"))
        )
        mAdapter.setOnItemClickListener { adapter, view, position ->
            {
                //去商品详情页面  避免head页面被单击
                if (position != 0) {
                    startActivity<ShopCommodityDetailActivity>()
                }
            }
        }

        mRvShop.adapter = mAdapter
        mRvShop.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildLayoutPosition(view)
                if (position % 2 == 0 && position != 0) {//右边
                    outRect.top = SizeUtils.dp2px(5f)
                    outRect.left = SizeUtils.dp2px(2.5f)
                    outRect.right = SizeUtils.dp2px(5f)
                } else if (position % 2 != 0) {
                    //左边

                    outRect.top = SizeUtils.dp2px(5f)
                    outRect.left = SizeUtils.dp2px(5f)
                    outRect.right = SizeUtils.dp2px(2.5f)
                }
            }
        })


    }

    /**
     * 頭佈局裝載
     */
    private lateinit var mTvIndicator: TextView
    private var mBannerCount = 0
    private fun setupHeaderView(images: List<String>): View {
        mBannerCount = images.size
        val headerContentView = layoutInflater.inflate(R.layout.header_shop_main, null)
        val banner = headerContentView.find<Banner>(R.id.mBanner)
        mTvIndicator = headerContentView.find(R.id.mTvIndicator)
        banner.setImageLoader(ImageLoader())
        banner.setOnPageChangeListener(this)
        banner.setImages(images)
        banner.start()
        return headerContentView

    }

    override fun createPresenter(): ShopPresenter = ShopPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvToShoppingCart -> {
                startActivity<ShoppingCarActivity>()
            }
            R.id.mTvToShoppingOrder -> {
                startActivity<ShoppingOrderActivity>()
            }
            else -> {
            }
        }

    }

    override fun bindListener() {
        super.bindListener()
        mTvToShoppingCart.onClick(this)
        mTvToShoppingOrder.onClick(this)
    }

    override fun onPageScrollStateChanged(state: Int) {


    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mTvIndicator.text = "${position + 1}/$mBannerCount"
    }
}