package com.upholstery.share.battery.mvp.modle.entity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/15
 * Description : 設備報損
 */
data class BreakageRequest(
        val sno:String,
        val desc:String,
        val orderno:String,
        val image:String
        ) {
}