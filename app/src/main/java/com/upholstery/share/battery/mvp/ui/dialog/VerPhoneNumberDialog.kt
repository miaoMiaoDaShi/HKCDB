package com.upholstery.share.battery.mvp.ui.dialog

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import com.upholstery.share.battery.R
import kotlinx.android.synthetic.main.dialog_ver_phone_num.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 確認手機號碼
 */

class VerPhoneNumberDialog : BaseDialogFragment() {
    private var blockCancel = {}
    private var blockConfirm = {}
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCreated = true
        refreshView()
        mTvCancel.onClick({
            blockCancel.invoke()
            dismiss()
        })

        mTvConfirm.onClick({
            blockConfirm.invoke()
            dismiss()
        })
    }

    override fun getResoureId(): Int {

        return R.layout.dialog_ver_phone_num
    }

    fun setListener(cancelBlock: () -> Unit, confirmBlock: () -> Unit) {
        blockCancel = cancelBlock
        blockConfirm = confirmBlock
    }

    fun setData(phoneNum: String) {
        val bundle = Bundle()
        bundle.putString("phoneNum", phoneNum)
        arguments = bundle
        refreshView()
    }

    /**
     * 刷新視圖
     */
    private var isCreated = false

    private fun refreshView() {
        if (isCreated) {
            mTvPhoneNum.text = arguments["phoneNum"].toString()
        }

    }

    companion object {
        fun newInstance(): VerPhoneNumberDialog {
            val bundle = Bundle()
            val warningDialog = VerPhoneNumberDialog()
            warningDialog.arguments = bundle
            return warningDialog
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isCreated = false
    }
}