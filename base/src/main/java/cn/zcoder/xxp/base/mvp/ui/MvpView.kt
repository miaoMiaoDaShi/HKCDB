package cn.zcoder.xxp.base.mvp.ui

import java.util.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/2/24
 * Description :
 */

interface MvpView {
    fun showLoading(type: Int)

    fun dismissLoading(type: Int)

    fun handlerError(type: Int,e:String)

    fun handlerSuccess(type: Int, data: Any)
}