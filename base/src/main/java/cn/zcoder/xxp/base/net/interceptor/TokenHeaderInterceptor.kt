package cn.zcoder.xxp.base.net.interceptor

import android.text.TextUtils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class TokenHeaderInterceptor : Interceptor {

    protected abstract fun getUid(): String

    protected abstract fun getToken(): String
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        initUserInfo()
        val token = if (TextUtils.isEmpty(originalRequest.header("token"))) getToken() else originalRequest.header("token")
        val uid = if (TextUtils.isEmpty(originalRequest.header("uid"))) getUid() else originalRequest.header("uid")
        var updateRequest: Request? = null
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(uid)) {
            updateRequest = originalRequest.newBuilder()
                    .header("token", token)
                    .header("uid", uid)
                    .build()
        }
        return chain.proceed(if (null == updateRequest) originalRequest else updateRequest)
    }

    protected abstract fun initUserInfo()

}
