package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.BankCardDetailResponse
import com.upholstery.share.battery.mvp.modle.entity.BankCardResponse
import com.upholstery.share.battery.mvp.presenter.BankCardPresenter
import com.upholstery.share.battery.mvp.presenter.BankCardPresenter.Companion.TYPE_LOAD_BANK_CARD
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_bank_card_list.*
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 信用卡列表 出现的加载情况(加载,添加)
 */

class BankCardListActivity : BaseMvpActivity<MvpView, BankCardPresenter>(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun showLoading(type: Int) {
        when (type) {
            TYPE_LOAD_BANK_CARD -> {
            }
            else -> {

            }
        }

    }

    override fun dismissLoading(type: Int) {
        when (type) {
            TYPE_LOAD_BANK_CARD -> {
            }
            else -> {
                //cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
            }
        }
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            TYPE_LOAD_BANK_CARD -> {
                showSnackBar(R.string.load_error, R.string.retry) {
                    onRefresh()
                }
            }
            else -> {
                //showSnackBar(R.string.add_failed, Snackbar.LENGTH_LONG)
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        if ((data as BankCardResponse).data.isEmpty()) {
            showSnackBar(R.string.no_data, Snackbar.LENGTH_INDEFINITE)
        }
        when (type) {
            TYPE_LOAD_BANK_CARD -> {
                mAdapter.replaceData(data.data)
            }
            else -> {
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun createPresenter(): BankCardPresenter = BankCardPresenter()

    override fun onRefresh() {
        getPresenter().getBankCards(TYPE_LOAD_BANK_CARD)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bank_card_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.bank_card))
                .setOnLeftImageListener { finish() }
                .setOnRightTextListener(getString(R.string.add), { add() })
                .showRightText(true)
        mSwipeRefreshBankList.setOnRefreshListener(this)

        mSwipeRefreshBankList.post {
            mSwipeRefreshBankList.isRefreshing = true
            onRefresh()
        }

        initRecyclerView()

    }

    private lateinit var mAdapter: BaseQuickAdapter<BankCardResponse.DataBean, BaseViewHolder>
    private fun initRecyclerView() {
//        0-银联 1-VISA 2-MASTER
        val types = arrayOf("银联", "VISA", "MASTER")
        mRvBankList.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = object : BaseQuickAdapter<BankCardResponse.DataBean, BaseViewHolder>(R.layout.recycler_bank_card) {
            override fun convert(helper: BaseViewHolder, item: BankCardResponse.DataBean) {
                helper.setText(R.id.mTvBankCardName, item.bankName)
                        .setText(R.id.mTvBankCardNo, item.bankNo)
                        .setText(R.id.mTvBankCardType, types[item.bankType])
            }

        }

        mAdapter.onItemClickListener = this
        mRvBankList.adapter = mAdapter


    }

    /**
     * 點擊進入銀行卡詳情頁面
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {
        startActivity<BankDetailActivity>("id" to (adapter.data[position] as BankCardResponse.DataBean).id)

    }

    /**
     * 信用卡的添加
     */
    private fun add() {
        startActivity<EditBankCardActivity>("type" to 0)

    }

}