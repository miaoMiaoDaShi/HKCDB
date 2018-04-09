package com.upholstery.share.battery.mvp.ui.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import com.upholstery.share.battery.R
import com.upholstery.share.ui.widget.dialog.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_warning.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class WarningDialog : BaseDialogFragment() {
    private lateinit var blockCancel: () -> Unit
    private lateinit var blockConfirm: () -> Unit
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvContent.text = arguments["content"].toString()
        mTvCancel.text = arguments["cancelText"].toString()
        mTvCancel.onClick({
            blockCancel.invoke()
            dismiss()
        })
        mTvConfirm.text = arguments["confirmText"].toString()
        mTvConfirm.onClick({
            blockConfirm.invoke()
            dismiss()
        })
    }

    override fun getResoureId(): Int {

        return R.layout.dialog_warning
    }

    fun setListener(cancelBlock: () -> Unit, confirmBlock: () -> Unit) {
        blockCancel = cancelBlock
        blockConfirm = confirmBlock
    }

    companion object {
        fun show(content: String, cancelText: String, confirmText: String, manager: FragmentManager): WarningDialog {
            val bundle = Bundle()
            bundle.putString("content", content)
            bundle.putString("cancelText", cancelText)
            bundle.putString("confirmText", confirmText)
            val warningDialog = WarningDialog()
            warningDialog.arguments = bundle
            warningDialog.show(manager, "warningDialog")
            return warningDialog
        }
    }
}