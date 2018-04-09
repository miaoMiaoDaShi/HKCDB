package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.toJson
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.RegexUtils
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.mvp.modle.entity.UserResponse
import io.reactivex.functions.Consumer

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
class LoginPresenter : GetVerCodePresenter<MvpView>() {
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
    /**
     * 手机号验证码登录
     */
    fun login(areaCode:String,phone: String, verCode: String, type: Int) {
        if (!RegexUtils.isMobileSimple(phone)) {
            getView().handlerError(0x15, "")
            return
        }
        if (verCode.isNullOrEmpty()) {
            getView().handlerError(0x13, "")
            return
        }
        addSubscribe(getApi().login(areaCode.replace("+",""),"4", null, phone, verCode)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as UserResponse).isOk) {
                        getView().handlerSuccess(type, it)
                        mUserInfo = toJson(it)
                    } else throw RuntimeException(it.resmsg)
                }, { getView().handlerError(type, it.message!!) }))
    }

}