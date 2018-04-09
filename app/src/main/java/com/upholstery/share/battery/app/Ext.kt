package com.upholstery.share.battery.app

import cn.zcoder.xxp.base.ext.getApi
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.mvp.modle.api.APIService

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
fun getApi(): APIService {
    return getApi(APIService::class.java)
}
