package cn.zcoder.xxp.base.net

import android.text.TextUtils


import java.util.concurrent.TimeUnit

import cn.zcoder.xxp.base.Configurator
import cn.zcoder.xxp.base.mvp.ui.MvpView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/1/8
 * Description :  你要确保 该类引用时  baseUrl  应该在之前设置
 */


object RetrofitClient {

    /**
     * 这里是关键
     *
     * @param disposable
     * @param <E>        你必须继承我的BaseDisposable
     * @param <T>        数据类型
     * @return
     * @see BaseDisposable
    </T></E> */
    fun <E : BaseDisposable<T>, T : BaseResponse> subscribe(disposable: BaseDisposable<T>): E {

        COMPOSITE_DISPOSABLE.add(disposable)
        disposable
                .observable
                .compose(getDefaultTransformer())
                .compose(getErrorTransformer)
                .subscribeWith(disposable as BaseDisposable<Any>)

        return disposable as E
    }

    /**
     * 抛出的异常转化成 自定义的异常
     */
    private class TransformToCustomException : Function<Throwable, Observable<*>> {

        @Throws(Exception::class)
        override fun apply(throwable: Throwable): Observable<*> {
            return Observable.error<Any>(ExceptionHandle.handleException(throwable))
        }
    }

    /**
     * 检测返回的数据是不是Ok的
     */
    private class HandleFun : Function<Any, Any> {
        @Throws(Exception::class)
        override fun apply(o: Any): Any {
            val response = o as BaseResponse
            if (!response.isOk) {
                //如果错误码和错误信息都是null,
                throw RuntimeException(if (TextUtils.isEmpty(response.status.toString() + "" + response.resmsg))
                    ""
                else
                    response.resmsg)
            }
            return o
        }
    }


    private val DEFAULT_TIMEOUT: Long = 30
    fun getHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
            .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

    private val COMPOSITE_DISPOSABLE = CompositeDisposable()

    /**
     * 默认使用此处的OkhttpCLient
     */
    fun getRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
            .client(Configurator.okHttpClient ?: getHttpClientBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Configurator.apiHost)


    fun <T> getApiService(service: Class<T>): T {
        return getRetrofitBuilder().build().create(service)
    }


    /**
     * 错误相关
     *
     * @return
     */
    val getErrorTransformer: ObservableTransformer<in Any, out Any>?
        get() =
            ObservableTransformer {
                it.map(HandleFun())
                        .onErrorResumeNext(TransformToCustomException())
            }

    /**
     * 线程的调度
     *
     * @return
     */
    val getSchedulersTransformer: ObservableTransformer<in Any, out Any>?
        get() = ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    /**
     * 线程的调度
     *
     * @return
     */
    fun <V : MvpView> getDefaultTransformer(view: V?, type: Int): ObservableTransformer<in Any, out Any>? {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe { view?.showLoading(type) }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { view?.dismissLoading(type) }

        }
    }

    fun getDefaultTransformer(): ObservableTransformer<in Any, out Any>? {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }


    fun clean() {
        COMPOSITE_DISPOSABLE.clear()
    }

}
