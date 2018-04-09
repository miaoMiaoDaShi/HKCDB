package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.parseData
import cn.zcoder.xxp.base.ext.toJson
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.Constant.Companion.KEY_USER_DETAIL_INFO
import com.upholstery.share.battery.app.getApi
import com.upholstery.share.battery.mvp.modle.entity.UserDetailResponse

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/1
 * Description : 修改用户资料
 */
class ModPersonalDataPresenter : UploadImageFilePresenter() {
    private var mUserDetailJson by Preference(KEY_USER_DETAIL_INFO, "")
    /**
     * 获取用户详细资料
     */
    fun getUserDetail(type: Int) {
        addSubscribe(getApi().getUserDetail()
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as UserDetailResponse).isOk) {
                        getView().handlerSuccess(type, it)
                        mUserDetailJson = toJson(it)
                    } else throw RuntimeException(it.resmsg)
                }, { getView().handlerError(type, it.message!!) }))
    }

    /**
     * 修改用户资料
     */
    fun modPersonalData(key: String, value: String, type: Int) {
        mapOf(key to value)
    }


}