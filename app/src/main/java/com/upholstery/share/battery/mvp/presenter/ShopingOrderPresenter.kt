package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 订单页面
 */
class ShopingOrderPresenter : RxPresenter<MvpView>() {
    /**
     * 根据类型获取订单列表
     */
    fun getShoppingOrder(orderType: Int, type: Int) {
        addSubscribe(getApi().getCommodityOrder(orderType, type)
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
     * 根据id获取订单信息
     */
    /**
     * 确认收货
     */
    fun confirmReceipt(orderNo: String, type: Int) {
        addSubscribe(getApi().confirmReceipt(orderNo)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
        /**
         * 评价订单
         */
    }
}