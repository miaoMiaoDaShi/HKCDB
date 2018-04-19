package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description : 借用记录详细信息
 */

class BorrowRecordDetailPresenter : RxPresenter<MvpView>() {
    /**
     * 根据id  获取相应的借用记录详细信息
     */
    fun getBorrowDetail(id: Int, type: Int) {
        addSubscribe(getApi().getBorrowRecordDetail(id)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }

    /**
     * 餘額支付
     */
    fun payByWallet(orderno: String, payType: Int = 3, point: Int, cuponId: Int, type: Int) {
        addSubscribe(getApi().payByWallet(orderno, payType, point, cuponId)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }

    /**
     * 支付寶支付
     */
    fun payByAlipay(orderno: String, payType: Int = 1, point: Int, cuponId: Int, type: Int) {
        addSubscribe(getApi().payByAlipay(orderno, payType, point, cuponId)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }

    /**
     * 微信支付
     */
    fun payByWeChat(orderno: String, payType: Int = 0, point: Int, cuponId: Int, type: Int) {
        addSubscribe(getApi().payByWechar(orderno, payType, point, cuponId)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }

    /**
     * 信用卡支付
     */
    fun payBybankCard(orderno: String, payType: Int = 2, point: Int, cuponId: Int,   backToken: String,
                      currency: String ,type: Int) {
        addSubscribe(getApi().payBybankCard(orderno, payType, point, cuponId,backToken, currency)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }
}