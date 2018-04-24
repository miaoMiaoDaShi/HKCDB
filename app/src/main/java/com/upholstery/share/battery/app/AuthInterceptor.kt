package com.upholstery.share.battery.app

import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.fromJson
import cn.zcoder.xxp.base.net.interceptor.TokenHeaderInterceptor
import com.upholstery.share.battery.mvp.modle.entity.UserResponse
import timber.log.Timber

class AuthInterceptor : TokenHeaderInterceptor() {
    private var mUserInfo by Preference(Constant.KEY_USER_INFO, "")
    private var mUserResponse: UserResponse? = null

    override fun getUid() = "${mUserResponse?.data?.uid}"
    override fun getToken() = mUserResponse?.data?.token ?: ""

    override fun initUserInfo() {
        mUserResponse = fromJson<UserResponse>(mUserInfo)
        Timber.i(mUserInfo)
    }
}