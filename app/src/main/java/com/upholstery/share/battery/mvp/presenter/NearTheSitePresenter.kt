package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description : 網點  商家列表
 */
class NearTheSitePresenter : UsingRecordPresenter() {
    /**
     * 获取附近网点列表
     */
    fun getNearTheSites(lat: String, lng: String, distance: String, type: Int) {
        addSubscribe(getApi().getNearTheSite(lat, lng, distance)
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
     * 獲取網點詳情
     */
    fun getNearTheSiteDetail(id: String, type: Int) {
        addSubscribe(getApi().getNearTheSiteDetail(id)
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
