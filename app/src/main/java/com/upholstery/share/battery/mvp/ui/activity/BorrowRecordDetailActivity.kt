package com.upholstery.share.battery.mvp.ui.activity

import android.annotation.SuppressLint
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.BorrowRecordDetailResponse
import com.upholstery.share.battery.mvp.presenter.BorrowRecordDetailPresenter
import com.upholstery.share.battery.mvp.presenter.UsePresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import kotlinx.android.synthetic.main.activity_borrow_record_detail.*
import org.jetbrains.anko.startActivity


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/4/8
 * Description : 借用记录  详情页面
 *
 * intent type  0x10 扫码借用成功后
 *              0x11 订单页面  点击进入的
 */

class BorrowRecordDetailActivity : BaseMvpActivity<MvpView, BorrowRecordDetailPresenter>(),
        View.OnClickListener {
    private val mUsePresenter by lazy {
        UsePresenter()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvBreakage -> {
                mBorrowRecordDetailResponse?.let {
                    startActivity<BreakageActivity>("sno" to it.data[0].sno,
                            "orderNo" to it.data[0].orderno)
                }
            }
            R.id.mTvReportTheLoss -> {
                mBorrowRecordDetailResponse?.let {
                    mUsePresenter.lostDevice(it.data[0].orderno, 0x11)
                }
            }
            else -> {
            }
        }

    }

    private val mLoadDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }
    private val mId by lazy {
        intent.getIntExtra("id", 0)
    }

    override fun bindListener() {
        super.bindListener()
        mTvBreakage.onClick(this)
        mTvReportTheLoss.onClick(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_borrow_record_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                showSnackBar(R.string.load_error, R.string.retry) {
                    getPresenter().getBorrowDetail(mId, type)
                }
            }
            0x11 -> {
                showSnackBar(R.string.commit_failed)
            }
            else -> {
            }
        }

    }

    //订单状态订单状态1-使用中 2-待支付  3-已完成  4-报失 5-报损',
    private val types = arrayOf("使用中", "待支付", "已完成", "报失", "报损")
    private val typeColors = arrayOf(R.color.yellow, R.color.yellow, R.color.yellow, R.color.black,
            R.color.black, R.color.black)

    private var mBorrowRecordDetailResponse: BorrowRecordDetailResponse? = null
    @SuppressLint("SetTextI18n")
    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                mBorrowRecordDetailResponse = data as BorrowRecordDetailResponse
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
                tvId.text = borrowRecordDetail.sno
                //地点
                tvName.text = borrowRecordDetail.address

            }
            0x11 -> {
                toast(R.string.commit_success)
                finish()
            }
            else -> {
            }
        }

    }

    override fun createPresenter(): BorrowRecordDetailPresenter {
        mUsePresenter.attachView(this)
        return BorrowRecordDetailPresenter()
    }

    override fun initData() {
        super.initData()
        getPresenter().getBorrowDetail(mId, 0x10)
    }
}