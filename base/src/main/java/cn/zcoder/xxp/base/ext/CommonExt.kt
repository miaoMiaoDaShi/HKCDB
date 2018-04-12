package cn.zcoder.xxp.base.ext

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import cn.zcoder.xxp.base.net.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/26
 * Description :
 */

/**
 * 主要用于将数据实体转化为有序的map
 */
fun parseData(data: Any): Map<String, String>? {
    val gson = Gson()
    val json = gson.toJson(data)
    return gson.fromJson<Map<String, String>>(json, object : TypeToken<TreeMap<String, String>>() {

    }.type)
}

fun getRealFilePath(context: Context, uri: Uri?): String? {
    if (null == uri) {
        return null
    }
    val scheme = uri.scheme
    var data: String? = null
    if (scheme == null) {
        data = uri.path
    } else if (ContentResolver.SCHEME_FILE == scheme) {
        data = uri.path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    data = cursor.getString(index)
                }
            }
            cursor.close()
        }
    }
    return data
}


fun AppCompatActivity.showDialog(dialog: DialogFragment)  {
    dialog.show(supportFragmentManager, "TAG")
}

fun Fragment.showDialog(dialog: DialogFragment) {
    dialog.show(fragmentManager, "TAG")
}

fun dismissDialog(dialog: DialogFragment) {
    dialog.dismiss()
}


fun <T> getApi(service: Class<T>): T {
    return RetrofitClient.getRetrofitBuilder().build().create(service)
}

inline fun <reified T> fromJson(json: String): T? = Gson().fromJson(json, T::class.java)

fun toJson(obj: Any): String = Gson().toJson(obj)