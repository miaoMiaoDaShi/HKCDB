package cn.zcoder.xxp.base.mvp.ui.activity

import android.os.Bundle
import android.util.Log
import cn.zcoder.xxp.base.mvp.ActivityMvpDelegateImpl
import cn.zcoder.xxp.base.mvp.MvpDelegateCallback
import cn.zcoder.xxp.base.mvp.presenter.MvpPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/2/25
 * Description :
 */
abstract class BaseMvpActivity<V : MvpView, P : MvpPresenter<V>> : BaseActivity(),
        MvpView, MvpDelegateCallback<V, P> {

    val mMvpDelegate by lazy {
        ActivityMvpDelegateImpl<V, P>(this)
    }
    lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        mMvpDelegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        Log.i("ds", mMvpDelegate.toString())
       //

    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mMvpDelegate.onPause()
    }

    override fun onResume() {
        super.onResume()
        mMvpDelegate.onResume()
    }

    override fun onStart() {
        super.onStart()
        mMvpDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMvpDelegate.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        mMvpDelegate.onRestart()
    }

    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }

    override fun getMvpView(): V = this as V

    override fun getPresenter(): P = mPresenter
}