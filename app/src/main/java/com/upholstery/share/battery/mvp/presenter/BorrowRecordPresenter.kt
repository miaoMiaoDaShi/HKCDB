package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/7
 * Description : 使用记录
 */
class BorrowRecordPresenter : RxPresenter<MvpView>() {
    private var mPage = 0
    fun getBorrowRecord(type: Int) {
        when (type) {
            0x10 -> mPage = 1
            else -> {
                mPage++
            }
        }
        addSubscribe(getApi().getBorrowRecord(mPage)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    if (mPage > 1) mPage--
                    getView().handlerError(type, it.message!!)
                }))
    }
}