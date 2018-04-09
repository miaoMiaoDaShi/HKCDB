package cn.zcoder.xxp.base.ext

import android.widget.ImageView
import com.bumptech.glide.Glide


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description :
 */

fun ImageView.load(model: Any) {
    Glide.with(this).load(model).into(this)
}