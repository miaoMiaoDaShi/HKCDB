package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.ext.getApi
import cn.zcoder.xxp.base.mvp.presenter.BasePresenter
import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.RegexUtils
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.mvp.modle.api.APIService

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
open class GetVerCodePresenter<V : MvpView> : RxPresenter<V>() {
    /**
     * 獲取驗證碼
     */
    open fun getVerCode(areaCode:String,phone: String, userTo: String, type: Int) {
        if (RegexUtils.isMobileSimple(phone)) {
            getView().handlerSuccess(0x10, Unit)
        } else {
            getView().handlerError(0x10, "")
            return
        }
        addSubscribe(getApi().getVerCode(areaCode.replace("+",""),phone, userTo)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, { getView().handlerError(type, it.message!!) }))

    }
}