package com.upholstery.share.battery.mvp.ui.activity

import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.ShippingAddressPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/7
 * Description : 新增 编辑  收货地址
 */
class EditShippingAddressActivity:BaseMvpActivity<MvpView,ShippingAddressPresenter>() {
    private val mLoadDialog  by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    override fun getLayoutId(): Int  = R.layout.edit_shipping_address_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {//新增
                toast(R.string.add_failed)
            }
            0x11->{//编辑
                toast(R.string.mod_failed)
            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {//新增
                toast(R.string.add_success)
            }
            0x11->{//编辑
                toast(R.string.mod_success)
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): ShippingAddressPresenter  = ShippingAddressPresenter()
}