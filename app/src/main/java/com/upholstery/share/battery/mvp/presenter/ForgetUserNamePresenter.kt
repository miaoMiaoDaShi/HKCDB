package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.ToastUtils
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.getApi

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/6
 * Description :
 */
class ForgetUserNamePresenter:RxPresenter<MvpView> (){
    /**
     * 忘記用戶名
     */
    fun forgetUserName(phone:String,question:String,type:Int){

        if (phone.isEmpty()||question.isEmpty()) {
            getView().handlerError(0x10,"")
            return
        }
            addSubscribe(getApi().forgetUserName(phone, question)
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