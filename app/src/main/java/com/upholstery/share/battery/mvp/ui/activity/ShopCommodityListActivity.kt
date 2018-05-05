package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.ShopCommodityListPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 商品列表頁面
 */
class ShopCommodityListActivity:BaseMvpActivity<MvpView,ShopCommodityListPresenter>() {
    override fun getLayoutId(): Int  = R.layout.activity_shop_commodity_list

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

    override fun createPresenter(): ShopCommodityListPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}