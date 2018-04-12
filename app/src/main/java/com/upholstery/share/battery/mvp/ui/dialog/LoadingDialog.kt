package com.upholstery.share.battery.mvp.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.upholstery.share.battery.R
import kotlinx.android.synthetic.main.dialog_loading.*


/**
 * Created by zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2017/11/23
 * Description :
 */


class LoadingDialog : BaseDialogFragment() {


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.setText(getArguments().getString("title"));
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    override fun getResoureId(): Int {
        return R.layout.dialog_loading
    }

    companion object {
        private val TAG = "LoadingDialog"

        fun getInstance(title: String, manager: FragmentManager): LoadingDialog {
            val bundle = Bundle()
            bundle.putString("title", title)
            val tipDialog = LoadingDialog()
            tipDialog.arguments = bundle
            return tipDialog
        }

        fun getInstance(manager: FragmentManager): LoadingDialog {
            return getInstance("", manager)
        }
    }


}
