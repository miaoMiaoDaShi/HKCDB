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
 * Description :
 */
class InvitePresenter : RxPresenter<MvpView>() {
    /**
     * 頁數
     */
    private var mPage = 0

    fun getInvite(type: Int) {
        when (type) {
            0x10 -> mPage = 1
            else -> {
                mPage++
            }
        }
        addSubscribe(getApi().getInvite(mPage)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                    if (mPage > 1) mPage--
                }))
    }
}