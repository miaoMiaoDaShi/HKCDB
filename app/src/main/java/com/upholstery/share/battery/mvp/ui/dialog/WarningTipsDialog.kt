package com.upholstery.share.battery.mvp.ui.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import com.upholstery.share.battery.R
import kotlinx.android.synthetic.main.dialog_warning.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class WarningTipsDialog : BaseDialogFragment() {
    private var blockConfirm = {}
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCreated = true
        refreshView()


        mTvConfirm.onClick({
            blockConfirm.invoke()
            dismiss()
        })
    }

    override fun getResoureId(): Int {

        return R.layout.dialog_warning
    }

    fun setListener( confirmBlock: () -> Unit) {
        blockConfirm = confirmBlock
    }

    fun setData(content: String,  confirmText: String) {
        val bundle = Bundle()
        bundle.putString("content", content)
        bundle.putString("confirmText", confirmText)
        arguments = bundle
        refreshView()
    }

    /**
     * 刷新視圖
     */
    private var isCreated = false

    private fun refreshView() {
        if (isCreated) {
            mTvContent.text = arguments["content"].toString()
            mTvConfirm.text = arguments["confirmText"].toString()
        }

    }

    companion object {
        fun newInstance(content: String, confirmText: String): WarningTipsDialog {
            val bundle = Bundle()
            bundle.putString("content", content)
            bundle.putString("confirmText", confirmText)
            val warningDialog = WarningTipsDialog()
            warningDialog.arguments = bundle
            return warningDialog
        }

        fun show(content: String,  confirmText: String, manager: FragmentManager): WarningTipsDialog {
            val bundle = Bundle()
            bundle.putString("content", content)
            bundle.putString("confirmText", confirmText)
            val warningDialog = WarningTipsDialog()
            warningDialog.arguments = bundle
            warningDialog.show(manager, "warningDialog")
            return warningDialog
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isCreated = false
    }
}