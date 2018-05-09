package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.HtmlContentResponse
import com.upholstery.share.battery.mvp.presenter.CustomerServicePresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_service.*
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class CustomerServiceActivity : BaseMvpActivity<MvpView, CustomerServicePresenter>(), View.OnClickListener {
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
    }

    override fun handlerSuccess(type: Int, data: Any) {
        data as HtmlContentResponse
        var title = ""
        when (type) {
            0x10 -> {
                title = getString(R.string.use_compass)
            }
            0x11 -> {
                title = getString(R.string.use_rule)
            }
            0x12 -> {
                title = getString(R.string.privacy_policy)
            }
            0x13 -> {
                title = getString(R.string.use_compass)
            }

            else -> {
            }
        }
        startActivity<WebActivity>("title" to title,
                "html" to data.data.content)
    }

    override fun createPresenter(): CustomerServicePresenter = CustomerServicePresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mRlUseCompass -> {
                getPresenter().getHtmlByKey("manual", 0x10)

            }
            R.id.mRlUseRule -> {
                getPresenter().getHtmlByKey("termsandconditio", 0x11)
            }
            R.id.mRlPrivacyPolicy -> {
                getPresenter().getHtmlByKey("privacypolicy", 0x12)
            }
            R.id.mRlHotIssue -> {
                getPresenter().getHtmlByKey("manual", 0x10)
            }
            R.id.mRlFeedback -> {
                startActivity<FeedbackActivity>()
            }
            else -> {
            }
        }

    }

    override fun getLayoutId(): Int {

        return R.layout.activity_service
    }

    override fun bindListener() {
        super.bindListener()
        mRlUseCompass.onClick(this)
        mRlUseRule.onClick(this)
        mRlPrivacyPolicy.onClick(this)
        mRlHotIssue.onClick(this)
        mRlFeedback.onClick(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.customer_service)
                .setOnLeftImageListener { finish() }
    }

}