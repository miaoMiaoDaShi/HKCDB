package com.upholstery.share.battery.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cn.zcoder.xxp.base.ext.dismissDialog
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.fragment.BaseMvpFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.formatx
import com.upholstery.share.battery.event.ShoppingOrderRefreshEvent
import com.upholstery.share.battery.mvp.modle.entity.CommodityOrderListResponse
import com.upholstery.share.battery.mvp.presenter.ShopingOrderPresenter
import com.upholstery.share.battery.mvp.ui.activity.EvaluationOfTheOrderActivity
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_shipping_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.startActivity

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/9
 * Description : 購物訂單
 *
 * 1-已支付  2-已收货
'商品类型 1-积分商品  2-商城商品',

 */
class ShoppingOrderFragment : BaseMvpFragment<MvpView, ShopingOrderPresenter>()
        , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    companion object {
        fun newInstance(type: Int): ShoppingOrderFragment {
            val bundle = Bundle()
            bundle.putInt("type", type)
            val fragment = ShoppingOrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(fragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.fragment_shipping_order

    override fun showLoading(type: Int) {
        when (type) {
            0x10 -> {
                showDialog(mLoadingDialog)
            }
            else -> {
            }
        }
    }


    override fun dismissLoading(type: Int) {
        when (type) {
            0x10 -> {
                dismissDialog(mLoadingDialog)
            }
            else -> {
            }
        }
        mSwipeRefreshShippingOrder.isRefreshing = false
    }

    override fun handlerError(type: Int, e: String) {
        showSnackBar(R.string.load_error, R.string.retry) { onRefresh() }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0 -> {//獲取數據

            }
            0x10 -> {//確認收貨
                toRefresh(ShoppingOrderRefreshEvent())
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): ShopingOrderPresenter = ShopingOrderPresenter()
    override fun lazyLoad() {
        super.lazyLoad()
        onRefresh()
    }

    private val mType by lazy {
        arguments.getInt("type")
    }

    override fun initData() {
        super.initData()

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initRecyclerView()
        mSwipeRefreshShippingOrder.setOnRefreshListener(this)
    }

    private lateinit var mAdapter: BaseQuickAdapter<CommodityOrderListResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
        mRvShippingOrder.layoutManager = LinearLayoutManager(context)
        mAdapter = object : BaseQuickAdapter<CommodityOrderListResponse.DataBean, BaseViewHolder>
        (R.layout.recycler_shopping_order) {
            override fun convert(helper: BaseViewHolder, item: CommodityOrderListResponse.DataBean) {
                helper
                        .setText(R.id.mTvCommodityName, item.name)
                        .setText(R.id.mTvCommodityCount, "x ${item.number}")
                        .setText(R.id.mTvCommodityCost, "$ ${item.point}")
                        .setText(R.id.mTvCommodityCountSum, "${item.number}".formatx(getString(R.string.format_commodity)))
                        .setText(R.id.mTvCommodityCostSum, "$ ${item.point.toInt() * item.number}")
                        .setText(R.id.mBtnConfirm,
                                if (mType == 1) getString(R.string.confirm_receiving)
                                else getString(R.string.evaluation_order))
                        .addOnClickListener(R.id.mBtnConfirm)
                        .getView<ImageView>(R.id.mIvCommodityImage).load(item.image)


            }

        }
        mAdapter.setOnItemChildClickListener(this)
        mRvShippingOrder.adapter

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (view.id) {
            R.id.mBtnConfirm -> {
                val data = adapter.data[position] as CommodityOrderListResponse.DataBean
                when (mType) {
                    1 -> {//確認收貨
                        getPresenter().confirmReceipt(data.orderNo, 0x10)
                    }
                    2 -> {//待評價
                        startActivity<EvaluationOfTheOrderActivity>("orderNo" to data.orderNo)
                    }
                    else -> {
                    }
                }
            }
            else -> {
            }
        }

    }

    /**
     * 刷新數據
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun toRefresh(event: ShoppingOrderRefreshEvent) {
        mSwipeRefreshShippingOrder.post {
            mSwipeRefreshShippingOrder.isRefreshing = true
            onRefresh()
        }
    }

    override fun onRefresh() {
        getPresenter().getShoppingOrder(mType, 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }
}