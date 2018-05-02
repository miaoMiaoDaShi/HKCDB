package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.toJson
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.RegexUtils
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.mvp.modle.entity.UserResponse

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
class LoginPresenter : VerPhonePresenter<MvpView>() {
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
    /**
     * 手机号验证码登录
     */
    fun login(phone: String,  type: Int) {
//        if (!RegexUtils.isMobileSimple(phone)) {
//            getView().handlerError(0x10, "")
//            return
//        }
        addSubscribe(getApi().login("4", null, phone,"")
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as UserResponse).isOk) {
                        getView().handlerSuccess(type, it)
                        mUserInfo = toJson(it)
                    } else throw RuntimeException(it.resmsg)
                }, { getView().handlerError(type, it.message!!) }))
    }

}