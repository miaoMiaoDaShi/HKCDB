package com.upholstery.share.battery.app

import android.content.Context
import android.widget.ImageView
import cn.zcoder.xxp.base.ext.load
import com.youth.banner.loader.ImageLoader

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : banner使用的imageLoder
 */
class ImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        imageView?.load(path?:"")
    }
}