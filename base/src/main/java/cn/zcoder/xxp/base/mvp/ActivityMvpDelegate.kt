package cn.zcoder.xxp.base.mvp

import android.os.Bundle
import cn.zcoder.xxp.base.mvp.presenter.MvpPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/2/25
 * Description :
 */
interface ActivityMvpDelegate<V : MvpView, P : MvpPresenter<V>> {

    fun onCreate(bundle: Bundle?)


    fun onDestroy()


    fun onPause()


    fun onResume()

    fun onStart()


    fun onStop()

    fun onRestart()

}