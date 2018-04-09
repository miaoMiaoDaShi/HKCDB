package com.upholstery.share.battery.mvp.ui.activity

import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import kotlinx.android.synthetic.main.activity_service.*
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 */

class CustomerServiceActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id. mRlUseCompass-> {
            }
            R.id. mRlUseRule-> {
            }
            R.id. mRlPrivacyPolicy-> {
            }
            R.id. mRlHotIssue-> {
            }
            R.id. mRlFeedback-> {
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
                .setTitle(R.string.customer_service )
                .setOnLeftImageListener { finish() }
    }

}