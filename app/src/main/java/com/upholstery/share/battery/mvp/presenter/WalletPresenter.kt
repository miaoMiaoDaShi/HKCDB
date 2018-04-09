package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description : 錢包
 */
class WalletPresenter : RxPresenter<MvpView>() {
    fun getWallet() {
        addSubscribe(getApi().getWallet()
                .compose(RetrofitClient.getDefaultTransformer(getView(), 0))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(0, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(0, it.message!!)
                }))
    }
}
