package com.upholstery.share.battery.mvp.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import org.xml.sax.Attributes

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/5/9
 * Description :
 */
class MyCompoundButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CompoundButton(context, attrs, defStyleAttr) {
    override fun toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
        if (!isChecked) {
            super.toggle()
        }
    }
}