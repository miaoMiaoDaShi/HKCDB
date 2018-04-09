package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.app.getApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description : 圖片文件上傳
 */
open class UploadImageFilePresenter : RxPresenter<MvpView>() {
    fun uploadImageFile(path: String, type: Int) {
        val imageFile = File(path)
        val requestFile = RequestBody.create(MediaType.parse("image/png"), imageFile)
        val part = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile)
        addSubscribe(getApi().uploadImageFile(part)
                .compose(RetrofitClient.getDefaultTransformer(getView(), type))
                .subscribe({
                    if ((it as BaseResponse).isOk) {
                        getView().handlerSuccess(type, it)
                    } else throw RuntimeException(it.resmsg)
                }, { getView().handlerError(type, it.message!!) }))
    }
}