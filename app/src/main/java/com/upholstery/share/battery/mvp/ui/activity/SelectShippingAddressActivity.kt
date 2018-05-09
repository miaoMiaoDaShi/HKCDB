package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.ShippingAddressListResponse
import com.upholstery.share.battery.mvp.presenter.ShippingAddressPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_select_shipping_address.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/8
 * Description : 選擇收貨地址
 */
class SelectShippingAddressActivity : BaseMvpActivity<MvpView, ShippingAddressPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_select_shipping_address

    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.load_shipping_address_failed)
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
            else -> {
            }
        }
    }

    override fun start() {
        super.start()

    }

    override fun onResume() {
        super.onResume()
        //加载收货地址列表
        getPresenter().getShippingAddressList(0x10)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.select_shipping_address)
                .setOnLeftImageListener { finish() }
                .showRightText(true)
                .setOnRightTextListener(getString(R.string.manage)) {
                    /**
                     * 地址管理頁面
                     */
                    startActivity<ShoppingAddressManageActivity>()
                }

        initRecyclerView()
    }

    private lateinit var mAdapter: BaseQuickAdapter<ShippingAddressListResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
        mRv.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<ShippingAddressListResponse.DataBean, BaseViewHolder>
        (R.layout.recycler_select_shipping_address) {
            override fun convert(helper: BaseViewHolder, item: ShippingAddressListResponse.DataBean) {
                helper
                        .setText(R.id.mTvReceiveName, item.linkman.format(getString(R.string.format_receive_name)))
                        .setText(R.id.mTvPhone, item.phone)
                        .setText(R.id.mTvShippingAddress, item.address)
                        .setVisible(R.id.mTvIsDefault, item.isDefault == 1)
            }

        }
        mRv.adapter = mAdapter
        mRv.addItemDecoration(object :RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                if(parent.getChildLayoutPosition(view)%2!=0){
                    outRect.top = SizeUtils.dp2px(10f)
                }
            }
        })
        mAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                val data = adapter.data[position] as ShippingAddressListResponse.DataBean
                val intent = Intent()
                intent.putExtra("data", data)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }


        }
    }

    override fun createPresenter(): ShippingAddressPresenter = ShippingAddressPresenter()
}