package com.upholstery.share.battery.mvp.ui.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import cn.zcoder.xxp.base.ext.onClick
import com.upholstery.share.battery.R
import kotlinx.android.synthetic.main.dialog_bank_card.*

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/22
 * Description : 銀行卡詳情彈出的對話框
 */
class BankDetailDialog:BaseDialogFragment() {
    override fun getResoureId(): Int  = R.layout.dialog_bank_card

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvToEdit.onClick {
            mToEdit?.invoke()
        }
        mTvToDel.onClick {
            mToDel?.invoke()
        }

    }

    override fun onResume() {
        super.onResume()
        super.onStart()
        val dialogWindow = dialog.window
        val lp = dialogWindow!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        dialogWindow.setGravity(Gravity.BOTTOM)
        dialogWindow.attributes = lp
    }

    private var mToEdit:(()->Unit)? = null
    private var mToDel:(()->Unit)? = null

    fun setOnClickListener(toEdit:()->Unit,toDel:()->Unit){
        this.mToEdit = toEdit
        this.mToDel  = toDel
    }
}