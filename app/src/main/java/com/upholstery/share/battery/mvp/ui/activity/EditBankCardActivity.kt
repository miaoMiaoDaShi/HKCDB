package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.BankCardPresenter

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/17
 * Description : 添加  编辑 银行卡
 */
class EditBankCardActivity:BaseMvpActivity<MvpView,BankCardPresenter>() {
    override fun getLayoutId(): Int  = R.layout.activity_edit_bank_card

    override fun showLoading(type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading(type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handlerError(type: Int, e: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handlerSuccess(type: Int, data: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPresenter(): BankCardPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}