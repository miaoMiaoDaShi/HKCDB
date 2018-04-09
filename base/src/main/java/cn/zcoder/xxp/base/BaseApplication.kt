package cn.zcoder.xxp.base

import android.app.Application
import android.support.annotation.NonNull
import android.util.Log
import cn.zcoder.xxp.base.app.FakeCrashLibrary
import com.alibaba.android.arouter.launcher.ARouter
import com.upholstery.share.base.BuildConfig
import timber.log.Timber


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/27
 * Description :
 */

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ARouter.openLog()    // 打印日志
        ARouter.openDebug()
        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree());
        }
    }
}

private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, @NonNull message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        FakeCrashLibrary.log(priority, tag, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                FakeCrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                FakeCrashLibrary.logWarning(t)
            }
        }
    }

}