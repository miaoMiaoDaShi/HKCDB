package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.ConfirmAnOrderPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 確認訂單
 */
class ConfirmAnOrderActivity :BaseMvpActivity<MvpView, ConfirmAnOrderPresenter>(){
    override fun getLayoutId(): Int  = R.layout.activity_confirm_an_order

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

    override fun createPresenter(): ConfirmAnOrderPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}