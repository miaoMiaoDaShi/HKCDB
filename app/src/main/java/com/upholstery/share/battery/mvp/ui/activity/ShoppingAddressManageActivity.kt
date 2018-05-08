package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.format
import com.upholstery.share.battery.mvp.modle.entity.ShippingAddressListResponse
import com.upholstery.share.battery.mvp.presenter.ShippingAddressPresenter
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_shipping_address_manager.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/7
 * Description :收货地址列表页面(管理頁面)
 */
class ShoppingAddressManageActivity : BaseMvpActivity<MvpView, ShippingAddressPresenter>(), BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {



    override fun getLayoutId(): Int = R.layout.activity_shipping_address_manager

    override fun showLoading(type: Int) {

    }

    override fun dismissLoading(type: Int) {
        mSwipeRefresh.isRefreshing = false
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
            }
            0x11 -> {
                toast(R.string.del_failed)
            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                data as ShippingAddressListResponse
                mAdapter.replaceData(data.data)
            }
            0x11 -> {
                toast(R.string.del_success)
                mAdapter.notifyDataSetChanged()
            }
            else -> {
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.manage_shipping_address)
                .setOnLeftImageListener { finish() }
        mSwipeRefresh.setOnRefreshListener(this)
        initRecyclerView()

    }

    override fun bindListener() {
        super.bindListener()
        mBtnConfirm.onClick {
            addAddress()
        }
    }

    /**
     * 添加收貨地址
     */
    private fun addAddress() {
        startActivity<EditShippingAddressActivity>("type" to 0x10)

    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {

        getPresenter().getShippingAddressList(0x10)
    }

    private lateinit var mAdapter: BaseQuickAdapter<ShippingAddressListResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<ShippingAddressListResponse.DataBean, BaseViewHolder>
        (R.layout.recycler_shipping_manager) {
            override fun convert(helper: BaseViewHolder, item: ShippingAddressListResponse.DataBean) {
                helper
                        .setText(R.id.mTvReceiveName, item.linkman.format(getString(R.string.format_receive_name)))
                        .setText(R.id.mTvPhone, item.phone)
                        .setText(R.id.mTvShippingAddress, item.address.format(getString(R.string.format_shipping_address)))
                        .setText(R.id.mTvReceiveName, item.linkman)
                        .addOnClickListener(R.id.mBtnEdit)
                        .addOnClickListener(R.id.mBtnDel)

                helper.getView<Button>(R.id.mBtnEdit).setTag(item)
                helper.getView<Button>(R.id.mBtnDel).setTag(item)

            }

        }
        mRv.adapter = mAdapter
        mAdapter.setOnItemChildClickListener(this)


    }

    private val mWarningDialog by lazy {
        WarningDialog.newInstance(getString(R.string.confirm_del), getString(R.string.cancel), getString(R.string.confirm))
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
        when (view.id) {
            R.id.mBtnDel -> {
                mWarningDialog.setListener({}, {
                    delAddress(view.getTag() as ShippingAddressListResponse.DataBean)
                })
                showDialog(mWarningDialog)
            }
            R.id.mBtnEdit -> {
                editAddress(view.getTag() as ShippingAddressListResponse.DataBean)
            }
            else -> {
            }
        }
    }
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {


    }

    /**
     * 編輯地址
     */
    private fun editAddress(bean: ShippingAddressListResponse.DataBean) {
        startActivity<EditShippingAddressActivity>("type" to 0x11 ,"data" to bean)

    }

    /**
     * 刪除相應的收貨地址
     */
    private fun delAddress(bean: ShippingAddressListResponse.DataBean) {
        getPresenter().delAddress(bean.id, 0x11)
    }

    override fun createPresenter(): ShippingAddressPresenter = ShippingAddressPresenter()
}