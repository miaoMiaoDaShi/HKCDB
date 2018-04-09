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
 * Time :  2018/3/31
 * Description : 意見反饋
 */
class FeedbackPresenter : UploadImageFilePresenter() {
    fun feedback(imagePath: String, content: String, type: Int) {
        if (content.isEmpty()) {
            ToastUtils.showLong(R.string.content_not_be_null)
            return
        }
//        if(imagePath.isEmpty()){
//
//        }

        addSubscribe(getApi().feedback(imagePath, content)
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