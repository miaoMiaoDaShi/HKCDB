package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.RegexUtils
import com.upholstery.share.battery.app.getApi

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/3
 * Description : 注册
 */
class RegisterPresenter : VerPhonePresenter<MvpView>() {
    fun register(areaCode: String, phone: String, code: String, surName: String, realname: String,
                 email: String, fatherId: String, type: Int) {
//        if (RegexUtils.isMobileSimple(phone)) {
//            getView().handlerSuccess(0x10, Unit)
//        } else {
//            getView().handlerError(0x10, "")
//            return
//        }

        if (code.isEmpty() || surName.isEmpty() || realname.isEmpty()) {
            getView().handlerError(0x11, "")
            return
        }

        addSubscribe(getApi().register(areaCode.replace("+", ""), phone, code, surName, realname,email, fatherId)               .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, {
                    getView().handlerError(type, it.message!!)
                }))
    }
}
