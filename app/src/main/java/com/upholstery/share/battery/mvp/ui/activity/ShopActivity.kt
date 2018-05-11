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
import cn.zcoder.xxp.base.ext.toast
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

    /**
     * 头布局
     */
    private val mHeaderView by lazy {
        setupHeaderView()
    }

    /**
     * bannerView
     */
    private val mBanner by lazy {
        mHeaderView.find<Banner>(R.id.mBanner).apply {
            setImageLoader(ImageLoader())
        }
    }

    /**
     * headerView中的分类recyclerView
     */
    private val mCategoryView by lazy {
        mHeaderView.find<RecyclerView>(R.id.mRvShopCategory).apply {
            val categoryIcon = arrayOf(
                    R.drawable.ic_shop_category_a,
                    R.drawable.ic_shop_category_b,
                    R.drawable.ic_shop_category_c,
                    R.drawable.ic_shop_category_d,
                    R.drawable.ic_shop_category_e
            )
            layoutManager = GridLayoutManager(applicationContext, 5)
            val adapter_ = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_shop_main_category) {
                override fun convert(helper: BaseViewHolder, item: String) {
                    val mTvCategoryTitle = helper.getView<TextView>(R.id.mTvCategoryTitle)
                    mTvCategoryTitle
                            .setCompoundDrawablesWithIntrinsicBounds(0, categoryIcon[helper.adapterPosition],
                                    0, 0)
                    mTvCategoryTitle.text = item
                }
            }
            adapter = adapter_

        }
    }
    /**
     * 指示器文字
     */
    private val mTvIndicator by lazy {
        mHeaderView.find<TextView>(R.id.mTvIndicator)
    }
    private var mBannerCount = 0

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
        //banner图片地址
        val images = listOf<String>(
                "http://d.hiphotos.baidu.com/image/pic/item/9345d688d43f8794f7940c9ede1b0ef41bd53a12.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/9345d688d43f8794f7940c9ede1b0ef41bd53a12.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/9345d688d43f8794f7940c9ede1b0ef41bd53a12.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/9345d688d43f8794f7940c9ede1b0ef41bd53a12.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/9345d688d43f8794f7940c9ede1b0ef41bd53a12.jpg"
        )
        //点击链接
        val clickLink = listOf<String>(
                "哈哈哈",
                "哈哈哈",
                "哈哈哈",
                "哈哈哈",
                "哈哈哈"
        )
        //分类名字  id
        val categoryTitle = listOf(
                "分类A",
                "分类B",
                "分类C",
                "分类D",
                "分类E"
        )
        //id值
        val categoryIds = listOf("45454", "45454", "44", "4444", "444")
        refreshHeader(images, categoryTitle, clickLink, categoryIds)
    }

    /**
     * 刷新banner的数据
     */
    private fun refreshHeader(images: List<String>, categoryTitle: List<String>
                              , clickLink: List<String>, categoryIds: List<String>) {
        mBannerCount = images.size
        mBanner.update(images)
        (mCategoryView.adapter as BaseQuickAdapter<String, BaseViewHolder>).run {
            replaceData(categoryTitle)
            setOnItemClickListener { adapter, view, position ->
                kotlin.run {
                    when (position) {
                        4 -> {//更多分类
                            startActivity<ShopCommodityCategoryActivity>()
                        }
                        else -> {
                        }
                    }
                }
            }

        }


    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.shop))
                .setOnLeftImageListener { finish() }

        initRecyclerView()
        handlerSuccess(0,1)

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
        mAdapter.addHeaderView(mHeaderView)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                //去商品详情页面
                startActivity<ShopCommodityDetailActivity>()
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
    private fun setupHeaderView(): View {
        val headerContentView = layoutInflater.inflate(R.layout.header_shop_main, null)
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
        // mTvIndicator.text = "${position + 1}/$mBannerCount"
    }
}