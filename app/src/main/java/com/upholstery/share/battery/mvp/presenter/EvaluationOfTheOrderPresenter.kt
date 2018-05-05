package com.upholstery.share.battery.mvp.presenter

import android.content.Context
import cn.zcoder.xxp.base.mvp.presenter.RxPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView
import com.upholstery.share.battery.R

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 評價訂單
 */
class EvaluationOfTheOrderPresenter:RxPresenter<MvpView>() {
    /**
     * 評價訂單
     */
    fun toEvaluationOrder(content: String, type: Int) {
        if (content.isEmpty()) {
            getView().handlerError(0,(getView() as Context).getString(R.string.content_not_be_null))
            return
        }


    }

}