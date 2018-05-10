package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.ext.getApi
import cn.zcoder.xxp.base.mvp.presenter.BasePresenter
import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.mvp.modle.api.APIService

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description : 手機號驗證
 */
open class VerPhonePresenter<V : MvpView> : RxPresenter<V>() {
    /**
     * 把手機號傳給后臺
     */
    open fun getVerCode(phone: String, userTo: String, type: Int) {
//        if (!RegexUtils.isMobileSimple(phone)) {
//            getView().handlerError(0x10, "")
//            return
//        }
        if(phone.isEmpty()){
            ToastUtils.showShort(R.string.the_required_field_cannot_be_empty)
            return
        }
        addSubscribe(getApi().verPhone(phone, userTo)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, { getView().handlerError(type, it.message!!) }))

    }
}