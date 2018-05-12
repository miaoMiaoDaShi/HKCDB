package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.RadioButton
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.CategoryTitleInfo
import com.upholstery.share.battery.mvp.presenter.ShopCommodityCategoryPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_shop_commodity_category.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 商城分类
 */
class ShopCommodityCategoryActivity : BaseMvpActivity<MvpView, ShopCommodityCategoryPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_shop_commodity_category

    override fun showLoading(type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading(type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handlerError(type: Int, e: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handlerSuccess(type: Int, data: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.category)
                .setOnLeftImageListener { finish() }
        initCategoryTitleRecyclerView()
        initCategoryChildRecyclerView()
    }

    /**
     * 标题栏
     */
    private val mCategoryTitleAdapter by lazy {
        object : BaseQuickAdapter<CategoryTitleInfo, BaseViewHolder>(R.layout.recycler_shop_category_page) {
            override fun convert(helper: BaseViewHolder, item: CategoryTitleInfo) {
                helper.setText(R.id.mRbCategoryTitle, item.title)
                        .addOnClickListener(R.id.mRbCategoryTitle)
                        .setChecked(R.id.mRbCategoryTitle, item.isCheck)
            }

        }.apply {
            setOnItemChildClickListener { adapter, view, position ->
                kotlin.run {
                    for (i in 0 until data.size) {
                        data[i].isCheck = position == i
                    }
                    notifyDataSetChanged()
                    mCategoryChildAdapter.replaceData(listOf("哈哈$position", "嘻嘻", "大薩達", "防守打法"))
                }
            }
        }
    }

    /**
     * 子分类
     */
    private val mCategoryChildAdapter by lazy {
        object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.recycler_shop_category_page) {
            override fun convert(helper: BaseViewHolder, item: String) {
                helper.setText(R.id.mRbCategoryTitle, item)
                        .addOnClickListener(R.id.mRbCategoryTitle)
            }

        }.apply {
            setOnItemChildClickListener { adapter, view, position ->
                kotlin.run {
                    toast("分類+$position")
                }
            }
        }
    }

    /**
     * 分类标题
     */
    private fun initCategoryTitleRecyclerView() {
        mRvCategoryTitles.layoutManager = LinearLayoutManager(applicationContext)
        mRvCategoryTitles.adapter = mCategoryTitleAdapter
        mCategoryTitleAdapter.replaceData(listOf(CategoryTitleInfo("哈哈"), CategoryTitleInfo("哈哈"),
                CategoryTitleInfo("哈哈"), CategoryTitleInfo("哈哈")))

    }

    /**
     * 子分类
     */
    private fun initCategoryChildRecyclerView() {
        mRvCategoryChild.layoutManager = GridLayoutManager(applicationContext, 3)
        mRvCategoryChild.adapter = mCategoryChildAdapter


    }

    override fun createPresenter(): ShopCommodityCategoryPresenter = ShopCommodityCategoryPresenter()
}