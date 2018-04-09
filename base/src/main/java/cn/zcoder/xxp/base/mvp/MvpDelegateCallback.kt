package cn.zcoder.xxp.base.mvp

import cn.zcoder.xxp.base.mvp.presenter.MvpPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/2/25
 * Description :
 */
interface MvpDelegateCallback<V : MvpView, P : MvpPresenter<V>> {
    /**
     * presenter的创建
     */
    fun createPresenter(): P

    /**
     * presenter的获取
     */
    fun getPresenter(): P

    /**
     * 为其设置presenter的获取
     */
    fun setPresenter(presenter: P)

    /**
     * 获取视图实例
     */
    fun getMvpView(): V
}