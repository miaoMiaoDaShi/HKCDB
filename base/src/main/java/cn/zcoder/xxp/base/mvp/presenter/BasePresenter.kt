package cn.zcoder.xxp.base.mvp.presenter

import cn.zcoder.xxp.base.mvp.ui.MvpView


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/24
 * Description :
 */

open class BasePresenter<V : MvpView> : MvpPresenter<V> {
    private var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    /**
     * 视图是否绑定
     */
    private val isAttached: Boolean
        get() = mView != null

    fun checkViewAttached() {
        if (!isAttached) throw MvpViewNotAttachedException()
    }

    fun getView(): V = mView!!


    private class MvpViewNotAttachedException internal constructor() :
            RuntimeException("Please call MvpPresenter.attachView(MvpView) before" + " requesting data to the MvpPresenter")
}