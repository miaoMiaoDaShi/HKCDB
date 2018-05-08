package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.mvp.modle.entity.ShippingAddressListResponse

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 確認訂單等....後續操作
 */
class ConfirmAnOrderPresenter :RxPresenter<MvpView>(){
    /**
     * 獲取默認地址  第一次
     */
    fun getDefaultShippingAddress(type: Int) {
        addSubscribe(getApi().getShippingAddressList()
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        it as ShippingAddressListResponse
                        it.data.forEach {
                            if (it.isDefault==1){
                                getView().handlerSuccess(type, it)
                            }
                        }

                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }
    /**
     * 確認訂單
     */
}