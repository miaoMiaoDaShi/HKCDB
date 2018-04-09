package cn.zcoder.xxp.base.mvp

import android.os.Bundle
import cn.zcoder.xxp.base.mvp.presenter.MvpPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView

class ActivityMvpDelegateImpl<V : MvpView, P : MvpPresenter<V>> : ActivityMvpDelegate<V, P> {
    constructor(mvpDelegateCallback: MvpDelegateCallback<V, P>) {
        if (mvpDelegateCallback == null) {
            throw NullPointerException("MvpDelegateCallback is null!")
        }

        mMvpDelegateCallback = mvpDelegateCallback
    }

    var mMvpDelegateCallback: MvpDelegateCallback<V, P>


    override fun onCreate(bundle: Bundle?) {
        mMvpDelegateCallback.setPresenter(mMvpDelegateCallback.createPresenter())
        mMvpDelegateCallback.getPresenter().attachView(mMvpDelegateCallback.getMvpView())
    }


    override fun onDestroy() {
        mMvpDelegateCallback.getPresenter().detachView()
    }

    override fun onPause() {
    }

    override fun onResume() {
    }


    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onRestart() {
    }

}