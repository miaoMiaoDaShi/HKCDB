package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.ShippingAddressPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/8
 * Description : 選擇收貨地址
 */
class SelectShippingAddressActivity :BaseMvpActivity<MvpView,ShippingAddressPresenter>(){
    override fun getLayoutId(): Int  = R.layout.activity_select_shipping_address

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

    override fun createPresenter(): ShippingAddressPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}