package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.event.ShoppingOrderRefreshEvent
import com.upholstery.share.battery.mvp.presenter.EvaluationOfTheOrderPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_evaluation_of_the_order.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.find

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/5
 * Description : 評價訂單
 */
class EvaluationOfTheOrderActivity : BaseMvpActivity<MvpView, EvaluationOfTheOrderPresenter>(),
        View.OnClickListener {


    /**
     * 加载等待框
     */
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_evaluation_of_the_order

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        toast(e)
    }

    override fun handlerSuccess(type: Int, data: Any) {
        toast(getString(R.string.evaluation_of_the_order_success))
        EventBus.getDefault().post(ShoppingOrderRefreshEvent())
        finish()
    }

    override fun onClick(v: View?) {

        v?.let {
            when (v.id) {
                R.id.mBtnCommit -> {
                    getPresenter().toEvaluationOrder(mEtContent.text.toString(),0x10)
                }
                else -> {
                }
            }
        }
    }

    /**
     * 订单号
     */
    private val mOrderNo by lazy {
        intent.getStringExtra("orderNo")
    }
    override fun bindListener() {
        super.bindListener()
        mBtnCommit.onClick(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(getString(R.string.evaluation))
                .setOnLeftImageListener { finish() }
    }

    override fun createPresenter(): EvaluationOfTheOrderPresenter = EvaluationOfTheOrderPresenter()
}