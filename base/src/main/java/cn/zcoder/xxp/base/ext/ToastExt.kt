package cn.zcoder.xxp.base.ext

import android.content.Context
import android.widget.Toast
import cn.zcoder.xxp.base.app.BaseConstant


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/24
 * Description : toast扩展文件
 */
fun Context.toast(content: String) {
    BaseConstant.showToast?.apply {
        setText(content)
        show()

    } ?: run {
        Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT).apply {
            BaseConstant.showToast = this
        }.show()
    }
}

fun Context.toast(stringId: Int) {
    toast(getString(stringId))
}


