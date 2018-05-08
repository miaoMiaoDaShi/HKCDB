package com.upholstery.share.battery.app

import cn.zcoder.xxp.base.ext.getApi
import cn.zcoder.xxp.base.net.RetrofitClient
import com.upholstery.share.battery.mvp.modle.api.APIService
import android.provider.MediaStore.Images.ImageColumns
import android.content.ContentResolver
import android.content.Context
import android.net.Uri


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
fun getApi(): APIService {
    return getApi(APIService::class.java)
}

fun Uri.getRealFilePath(context: Context): String? {
    val scheme = this.getScheme()
    var data: String? = null
    if (scheme == null)
        data = this.getPath()
    else if (ContentResolver.SCHEME_FILE == scheme) {
        data = this.getPath()
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(this, arrayOf(ImageColumns.DATA), null, null, null)
        if (null != cursor) {
            if (cursor!!.moveToFirst()) {
                val index = cursor!!.getColumnIndex(ImageColumns.DATA)
                if (index > -1) {
                    data = cursor!!.getString(index)
                }
            }
            cursor!!.close()
        }
    }
    return data
}

fun String.format(string: String): String {
    return String.format(string, this)
}

fun isEmptyOrNull(vararg string: String): Boolean {
    string.forEach {
        if (it.isNullOrEmpty()) return true
    }
    return false
}

