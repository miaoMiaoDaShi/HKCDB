package cn.zcoder.xxp.base.mvp.presenter

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.net.BaseDisposable
import cn.zcoder.xxp.base.net.BaseResponse
import cn.zcoder.xxp.base.net.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/24
 * Description :
 */

open class RxPresenter<V : MvpView> : BasePresenter<V>() {
    private val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }


    protected fun unSubscribe() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }
    }

    override fun detachView() {
        super.detachView()
        unSubscribe()
    }

    /**
     * 执行请求
     *
     * @param disposable
     * @param <E>
     * @param <T>
     * @return
    </T></E> */
    protected fun <E : BaseDisposable<T>, T : BaseResponse> subscribe(disposable: BaseDisposable<T>): E {
        return addSubscribe(RetrofitClient.subscribe(disposable)) as E
    }


    protected open fun addSubscribe(subscription: Disposable): Disposable {
        mCompositeDisposable.add(subscription)
        return subscription
    }
}