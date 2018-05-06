package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.mvp.presenter.ShopingOrderPresenter

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/6
 * Description : 購物訂單頁面
 */
class ShoppingOrderActivity:BaseMvpActivity<MvpView,ShopingOrderPresenter>() {
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

    override fun createPresenter(): ShopingOrderPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}