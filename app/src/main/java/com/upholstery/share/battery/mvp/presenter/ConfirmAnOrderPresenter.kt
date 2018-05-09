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
class ConfirmAnOrderPresenter : RxPresenter<MvpView>() {
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
                            if (it.isDefault == 1) {
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

    fun payCommodity(consignee: String,//收貨人姓名
                     phone: String,//電話號碼
                     province: String,//省
                     city: String,//市
                     area: String,//區
                     address: String,//詳細地址
                     time: String,
                     number: Int,
                     productId: String,
                     type: Int,
                     payWay: Int, typex: Int) {
        addSubscribe(getApi().payCommodity(consignee, phone, province, city, area, address, time, number, productId, type, payWay)
                .compose(RetrofitClient.getDefaultTransformer(getView(), typex))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(typex, it)

                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(typex, it.message!!)
                }))
    }
}