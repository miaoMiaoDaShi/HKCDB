package cn.zcoder.xxp.base.mvp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.zcoder.xxp.base.mvp.presenter.MvpPresenter
import cn.zcoder.xxp.base.mvp.ui.MvpView

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/2/25
 * Description :
 */
interface FragmentMvpDelegate<V : MvpView, P : MvpPresenter<V>> {


    fun onCreate(saved: Bundle?)

    fun onDestroy()

    fun onViewCreated(view: View?, savedInstanceState: Bundle?)


    fun onDestroyView()

    fun onPause()

    fun onResume()

    fun onStart()

    fun onStop()

    fun onActivityCreated(savedInstanceState: Bundle?)


    fun onAttach(activity: Activity)

    fun onDetach()
    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)


}