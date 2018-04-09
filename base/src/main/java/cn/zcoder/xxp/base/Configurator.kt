package cn.zcoder.xxp.base

import android.app.Application
import android.content.Context
import android.os.Environment
import cn.zcoder.xxp.base.app.Preference


import java.io.File
import java.util.HashMap

import okhttp3.OkHttpClient

/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/10/16
 * Description :
 */


object Configurator {
    private val CONFIG = HashMap<String, Any>()

    val okHttpClient: OkHttpClient?
        get() = get(ConfigKey.OK_HTTP_CLIENT)

    /**
     * 获取applicationContext
     *
     * @return
     */
    val context: Context
        get() = get(ConfigKey.APPLICATION_CONTEXT)

    /**
     * 获取PaiHost
     *
     * @return
     */
    val apiHost: String
        get() = get(ConfigKey.API_HOST)

    /**
     * 获取sp文件的名字
     */
    val spFileName: String
        get() = get(ConfigKey.SP_FILE_NAME)
    /**
     * 获取项目文件夹
     *
     * @return
     */
    val appFolder: String
        get() = get(ConfigKey.APP_FOLDER)

    fun withOkHttpClient(build: OkHttpClient): Configurator {
        put(ConfigKey.OK_HTTP_CLIENT, build)
        return this
    }

    fun withSPFileName(name: String = "config") {
        put(ConfigKey.SP_FILE_NAME, name)
        Preference.init(context, name)
    }


    private fun put(configKey: ConfigKey, `object`: Any) {
        CONFIG[configKey.name] = `object`
    }


    private operator fun <T> get(key: ConfigKey): T {
        return CONFIG[key.name] as T
    }

    /**
     * 配置APiHost
     *
     * @param host
     * @return
     */
    fun withApiHost(host: String): Configurator {
        put(ConfigKey.API_HOST, host)
        return this
    }

    /**
     * 配置项目的文件夹
     *
     * @param name
     * @return
     */
    fun withAppFolder(name: String): Configurator {
        put(ConfigKey.APP_FOLDER, Environment.getExternalStorageDirectory().toString() + File.separator + name)
        return this
    }

    /**
     * 配置ApplicationContext
     *
     * @param context
     * @return
     */
    fun init(context: Application): Configurator {
        put(ConfigKey.APPLICATION_CONTEXT, context)
        return this
    }


}
