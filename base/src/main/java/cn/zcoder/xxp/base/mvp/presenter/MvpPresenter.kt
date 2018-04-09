package cn.zcoder.xxp.base.mvp.presenter

import cn.zcoder.xxp.base.mvp.ui.MvpView


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/24
 * Description :
 */

interface MvpPresenter<T : MvpView> {
    fun attachView(view: T)

    fun detachView()
}