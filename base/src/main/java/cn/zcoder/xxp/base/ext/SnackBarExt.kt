package cn.zcoder.xxp.base.ext

import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.ViewGroup

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/7
 * Description : SnakeBar
 */

fun Activity.showSnackBar(content: String, duration: Int, actionText: String,
                          method: () -> Unit) {
    Snackbar.make((this.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), content, duration)
            .setAction(actionText) { method() }.show()
}

fun Fragment.showSnackBar(content: String, duration: Int, actionText: String,
                          method: () -> Unit) {
    Snackbar.make((this.activity.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), content, duration)
            .setAction(actionText) { method() }.show()
}

fun Activity.showSnackBar(contentId: Int, duration: Int, actionTextId: Int,
                          method: () -> Unit) {
    showSnackBar(getString(contentId), duration, getString(actionTextId), method)
}

fun Fragment.showSnackBar(contentId: Int, duration: Int, actionTextId: Int,
                          method: () -> Unit) {
    showSnackBar(getString(contentId), duration, getString(actionTextId), method)
}


fun Activity.showSnackBar(contentId: Int, actionTextId: Int,
                          method: () -> Unit) {
    showSnackBar(getString(contentId), Snackbar.LENGTH_SHORT, getString(actionTextId), method)
}

fun Fragment.showSnackBar(contentId: Int, actionTextId: Int,
                          method: () -> Unit) {
    showSnackBar(getString(contentId), Snackbar.LENGTH_SHORT, getString(actionTextId), method)
}

fun Activity.showSnackBar(contentId: Int) {
    Snackbar.make((this.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), getString(contentId), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(contentId: Int) {
    Snackbar.make((this.activity.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), getString(contentId), Snackbar.LENGTH_SHORT).show()
}

fun Activity.showSnackBar(contentId: Int, duration: Int) {
    Snackbar.make((this.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), getString(contentId), duration).show()
}

fun Fragment.showSnackBar(contentId: Int, duration: Int) {
    Snackbar.make((this.activity.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), getString(contentId), duration).show()
}

fun Activity.showSnackBar(content: String) {
    Snackbar.make((this.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), content, Snackbar.LENGTH_SHORT).show()
}

fun Activity.showSnackBar(content: String, duration: Int) {
    Snackbar.make((this.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), content, duration).show()
}

fun Fragment.showSnackBar(content: String) {
    Snackbar.make((this.activity.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), content, Snackbar.LENGTH_SHORT).show()
}
fun Fragment.showSnackBar(content: String,duration: Int) {
    Snackbar.make((this.activity.window.decorView
            .findViewById(android.R.id.content) as ViewGroup)
            .getChildAt(0), content, duration).show()
}
