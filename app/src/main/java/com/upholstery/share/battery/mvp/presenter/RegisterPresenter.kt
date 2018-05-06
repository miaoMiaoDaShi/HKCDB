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
    fun register( phone: String,  surName: String, realname: String,
                 fatherId: String,question:String, type: Int) {
//        if (RegexUtils.isMobileSimple(phone)) {
//            getView().handlerSuccess(0x10, Unit)
//        } else {
//            getView().handlerError(0x10, "")
//            return
//        }

        if (phone.isEmpty()||surName.isEmpty() || realname.isEmpty()||fatherId.isEmpty()||question.isEmpty()) {
            getView().handlerError(0x11, "")
            return
        }

        addSubscribe(getApi().register( phone,  surName, realname, fatherId,question)
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
