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
 * Description : 信用/货币页面
 */
class CreditsAndCurrencyPresenter : RxPresenter<MvpView>() {
    /**
     * 获取信用记录列表
     */
    fun getCreditRecordList(type: Int) {
        when (type) {
            0x10 -> mPage = 1
            else -> {
                mPage++
            }
        }
        addSubscribe(getApi().getCreditRecordList(mPage)
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
    /**
     * 获取货币记录列表
     */
    fun getCurrencyRecordList(type: Int) {
        when (type) {
            0x10 -> mPage = 1
            else -> {
                mPage++
            }
        }
        addSubscribe(getApi().getCurrencyRecordList(mPage)
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
    /**
     * 获取积分/货币信息
     */
    fun getCreditAndCurrency(type: Int) {
        addSubscribe(getApi().getCreditsAndCurrency()
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
     * 获取兑换商品列表
     */
    private var mPage = 0

    fun getCreditsCommodity(type: Int) {
        when (type) {
            0x11 -> mPage = 1
            else -> {
                mPage++
            }
        }
        addSubscribe(getApi().getCreditsCommodity(mPage)
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

    /**
     * 获取兑换商品的详细信息
     */
    fun getCreditCommodityDetail(id: String, type: Int) {
        addSubscribe(getApi().getCreditsCommodityDetail(id)
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