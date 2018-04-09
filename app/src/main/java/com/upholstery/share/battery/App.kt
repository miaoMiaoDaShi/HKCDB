package com.upholstery.share.battery

import cn.zcoder.xxp.base.BaseApplication
import cn.zcoder.xxp.base.Configurator
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.Utils
import com.upholstery.share.battery.app.AuthInterceptor


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/9
 * Description :
 */

class App : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        Configurator.init(this)
                .withApiHost("http://47.75.92.26/api/")
                .withOkHttpClient(RetrofitClient.getHttpClientBuilder()
                        .addInterceptor(AuthInterceptor())
                        .build())
                .withAppFolder("watch")
                .withSPFileName("")
    }
}