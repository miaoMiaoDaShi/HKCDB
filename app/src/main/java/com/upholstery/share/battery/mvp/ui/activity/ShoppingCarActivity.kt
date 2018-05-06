package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.ShopingCarPresenter
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import org.jetbrains.anko.find

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/6
 * Description : 購物車
 */
class ShoppingCarActivity:BaseMvpActivity<MvpView,ShopingCarPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_shoping_car

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
                .setTitle(R.string.shopping_car)
                .setOnLeftImageListener { finish() }
    }

    override fun createPresenter(): ShopingCarPresenter = ShopingCarPresenter()
}