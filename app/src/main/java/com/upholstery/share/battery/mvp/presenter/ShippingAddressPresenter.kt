package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.ToastUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.app.isEmptyOrNull

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 收货地址
 */
open class ShippingAddressPresenter : RxPresenter<MvpView>() {

    /**
     * 获取收货地址列表
     */
    fun getShippingAddressList(type: Int) {
        addSubscribe(getApi().getShippingAddressList()
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
     * 新增收货地址
     */
    fun addAddress(
            linkman: String,
            phone: String,
            province: String,
            city: String,
            area: String,
            address: String,
            postCode: String,
            type: Int) {
        if (isEmptyOrNull(linkman, phone, province, city, area, address, postCode)) {
            ToastUtils.showShort(R.string.the_required_field_cannot_be_empty)
            return
        }
        addSubscribe(getApi().addShippingAddress(linkman, phone, province, city, area, address, postCode)
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
     * 编辑收货地址(资料修改  是否为默认的收货地址)
     */
    fun editAddress(
            id: String,
            linkman: String,
            phone: String,
            province: String,
            city: String,
            area: String,
            address: String,
            postCode: String,
            type: Int) {
        if (id.isEmpty()) {
            ToastUtils.showShort("無效ID值")
            return
        }
        if (isEmptyOrNull(linkman, phone, province, city, area, address, postCode)) {
            ToastUtils.showShort(R.string.the_required_field_cannot_be_empty)
            return
        }
        addSubscribe(getApi().editShippingAddress(id, linkman, phone, province, city, area, address, postCode)
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
     * 删除收货地址
     */
    fun delAddress(id: Int, type: Int) {
        addSubscribe(getApi().delShippingAddress(id.toString())
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