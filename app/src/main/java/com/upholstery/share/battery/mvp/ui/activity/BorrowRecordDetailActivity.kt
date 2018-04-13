package com.upholstery.share.battery.mvp.ui.activity

import android.annotation.SuppressLint
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.BorrowRecordDetailResponse
import com.upholstery.share.battery.mvp.presenter.BorrowRecordDetailPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_borrow_record_detail.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description : 借用记录  详情页面
 */

class BorrowRecordDetailActivity : BaseMvpActivity<MvpView, BorrowRecordDetailPresenter>() {

    private val mLoadDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    private val mId by lazy {
        intent.getIntExtra("id", 0)
    }

    override fun getLayoutId(): Int = R.layout.activity_borrow_record_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
    }

    override fun handlerError(type: Int, e: String) {
        showSnackBar(R.string.load_error, R.string.retry) {
            getPresenter().getBorrowDetail(mId, 0)
        }
    }

    //订单状态订单状态1-使用中 2-待支付  3-已完成  4-报失 5-报损',
    private val types = arrayOf("使用中", "待支付", "已完成", "报失", "报损")
    private val typeColors = arrayOf(R.color.yellow, R.color.yellow, R.color.yellow, R.color.black,
            R.color.black, R.color.black)

    @SuppressLint("SetTextI18n")
    override fun handlerSuccess(type: Int, data: Any) {
        data as BorrowRecordDetailResponse
        val borrowRecordDetail = data.data[0]

        //订单状态
        tvUseStatus.text = types[borrowRecordDetail.statusX - 1]
        tvUseStatus.setTextColor(typeColors[borrowRecordDetail.statusX - 1])
        //使用时长
        tvUseTime.text = "${borrowRecordDetail.used}分钟"
        //费用
        tvMoney.text = String.format("%.2f元", borrowRecordDetail.cost / 100.0)
        //订单号
        tvNumber.text = borrowRecordDetail.orderno
        //充电宝id
        tvId.text = "${borrowRecordDetail.id}"
        //地点
        tvName.text = borrowRecordDetail.address


    }

    override fun createPresenter(): BorrowRecordDetailPresenter = BorrowRecordDetailPresenter()

    override fun initData() {
        super.initData()
        getPresenter().getBorrowDetail(mId, 0)
    }
}