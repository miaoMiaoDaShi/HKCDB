package cn.zcoder.xxp.base.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.zcoder.xxp.base.mvp.FragmentMvpDelegateImpl
import cn.zcoder.xxp.base.mvp.MvpDelegateCallback
import cn.zcoder.xxp.base.mvp.presenter.MvpPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/2/25
 * Description :
 */
abstract class BaseMvpFragment<V : MvpView, P : MvpPresenter<V>> : BaseFragment(), MvpView
        , MvpDelegateCallback<V, P> {
    val mMvpDelegate by lazy {
        FragmentMvpDelegateImpl<V, P>(this)
    }
    lateinit var mPresenter: P
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mMvpDelegate.onCreateView(inflater,container,savedInstanceState)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMvpDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mMvpDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mMvpDelegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)

    }

    override fun onPause() {
        super.onPause()
        mMvpDelegate.onPause()
    }

    override fun onStart() {
        super.onStart()
        mMvpDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMvpDelegate.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        mMvpDelegate.onDetach()
    }

    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }
    override fun getMvpView(): V = this as V

    override fun getPresenter(): P = mPresenter
}