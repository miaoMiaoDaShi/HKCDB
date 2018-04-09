package com.upholstery.share.battery.app

import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.fromJson
import cn.zcoder.xxp.base.net.interceptor.TokenHeaderInterceptor
import com.upholstery.share.battery.mvp.modle.entity.UserResponse

class AuthInterceptor : TokenHeaderInterceptor() {
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")

    override fun getUid() = "${fromJson<UserResponse>(mUserInfo)?.data?.uid}"
    override fun getToken() = fromJson<UserResponse>(mUserInfo)?.data?.token?:""

    override fun initUserInfo() {

    }
}