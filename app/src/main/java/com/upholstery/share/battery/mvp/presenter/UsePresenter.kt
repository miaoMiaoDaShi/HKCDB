package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi
import java.util.concurrent.TimeUnit

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description : 使用
 */
open class UsePresenter : UploadImageFilePresenter() {
    /**
     * 正在使用的(最新订单)
     */
    fun getUsingRecord(type: Int) {
        addSubscribe(getApi().getUsingOrder()
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
     * 借用设备
     */
    fun borrowOne(sno: String, nonce: String, type: Int) {
        addSubscribe(getApi().borrowOne(sno, nonce)
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
     * 开启仓位
     */
    fun openFreightSpace(sno: String, type: Int) {
        addSubscribe(getApi().openFreightSpace(sno)
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
     * 设备报失
     */
    fun lostDevice(orderNo: String, type: Int) {
        addSubscribe(getApi().lostDevice(orderNo)
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
     * 设备报损
     */
    fun breakageDevice(sno: String, desc: String, orderno: String, image: String, type: Int) {
        if(desc.isEmpty()||image.isEmpty()){
            getView().handlerError(0x12,"")
            return
        }
        addSubscribe(getApi().breakageDevice(sno, desc, orderno, image)
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