package com.upholstery.share.battery

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.zcoder.xxp.base.BaseApplication
import cn.zcoder.xxp.base.Configurator
import cn.zcoder.xxp.base.event.ErrorEvent
import cn.zcoder.xxp.base.net.RetrofitClient
import com.blankj.utilcode.util.Utils
import com.tencent.bugly.crashreport.CrashReport
import com.upholstery.share.battery.app.AuthInterceptor
import com.upholstery.share.battery.mvp.ui.activity.LoginActivity
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/9
 * Description :
 */

class App : BaseApplication() {


    var mActivity: AppCompatActivity? = null
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        CrashReport.initCrashReport(getApplicationContext(), "5e07f4e6bc", false);
        Configurator.init(this)
                .withApiHost("http://47.75.92.26/api/")
                .withOkHttpClient(RetrofitClient.getHttpClientBuilder()
                        .addInterceptor(AuthInterceptor())
                        .build())
                .withAppFolder("watch")
                .withSPFileName("")


        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity?) {

            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                mActivity = p0 as AppCompatActivity
            }
        })
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMustLogin(errorEvent: ErrorEvent) {
        when (errorEvent.code) {
            105, 106, 107 -> {//身份失效
                accountByAbnormal(getString(R.string.Identity_is_overdue))
            }
            108 -> {//賬號凍結
                accountByAbnormal(getString(R.string.suspend_an_account))
            }
            else -> {
            }
        }

    }


    protected open fun accountByAbnormal(content: String) {
        mActivity?.let {
            val warningDialog = WarningDialog
                    .newInstance(content, getString(R.string.cancel), getString(R.string.confirm))
            warningDialog.setListener({ System.exit(0) }, {
                val intent = Intent(this, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            })
            warningDialog.isCancelable = false
            warningDialog.show(it.supportFragmentManager, "hahahaah")
        }


    }

}