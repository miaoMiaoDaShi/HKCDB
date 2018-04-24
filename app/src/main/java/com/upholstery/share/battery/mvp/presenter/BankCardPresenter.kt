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
 * Description : 银行卡  信用卡 相关
 */

class BankCardPresenter : RxPresenter<MvpView>() {
    companion object {
        //加载银行卡列表
        const val TYPE_LOAD_BANK_CARD = 0x10
        //添加银行卡
        const val TYPE_ADD_BANK_CARD = 0x11
        //删除银行卡
        const val TYPE_DEL_BANK_CARD = 0x10
    }
    /**
     * 获取银行卡列表
     */
    fun getBankCards(type: Int) {
        addSubscribe(getApi().getBankCard()
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
     * 添加银行卡
     */
    fun addBankCard(name: String, bankName: String, bankNo: String, bankExpire: String,
                    bankCvv: String,area:String, type: Int) {
        if(name.isEmpty()||bankName.isEmpty()||bankNo.isEmpty()){
            getView().handlerError(0x11,"")
            return
        }
        addSubscribe(getApi().addBankCard(name, bankName, bankNo, bankExpire, bankCvv,area)
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
     * 删除银行卡
     */
    fun delBankCard(id: String, type: Int) {
        addSubscribe(getApi().delBankCard(id)
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
     * 获取银行卡详情
     */
    fun getBankCardDetail(id: String, type: Int) {
        addSubscribe(getApi().getBankCardDetail(id)
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
     * 修改银行卡  仅支持 修改地区
     */
    fun editBankCard(id: String, area:String, type: Int) {

        addSubscribe(getApi().editBankCard(id,area)
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