package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.presenter.CouponPresenter

/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/2
 * Description : 优惠券的选择
 */
class SelectCouponActivity : BaseMvpActivity<MvpView, CouponPresenter>() {
    override fun getLayoutId(): Int = R.layout.activity_recycle

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

    override fun createPresenter(): CouponPresenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 选择优惠券
     * @param 优惠券Id
     * @param 减免金额(分)
     */
    private fun selectCoupon(couponId: String, money: Int) {
        val intent = Intent()
        intent.putExtra("id", couponId)
        intent.putExtra("money", money)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}