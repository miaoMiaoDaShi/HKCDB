package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.upholstery.share.battery.R
import com.amap.api.maps.AMap
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.os.SystemClock
import android.view.animation.BounceInterpolator
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker


class MainActivity : BaseMvpActivity<MvpView, HomePresenter>(), View.OnClickListener {
    override fun showLoading(type: Int) {


    }

    override fun dismissLoading(type: Int) {
    }

    override fun handlerError(type: Int, e: String) {
    }

    override fun handlerSuccess(type: Int, data: Any) {
    }

    override fun createPresenter(): HomePresenter = HomePresenter()
    lateinit var mAmap: AMap

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun bindListener() {
        super.bindListener()
        mIvMine.onClick(this)
        mIvProtocol.onClick(this)
        mIvAbout.onClick(this)
        mIvLocation.onClick(this)
        mIvRefresh.onClick(this)
        mTvBorrow.onClick(this)
        mTvRepay.onClick(this)
        mTvToWallet.onClick(this)
        mTvToUseRecord.onClick(this)
        mTvToMsg.onClick(this)
        mTvToCredits.onClick(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        checkPermission()
        mMapView.onCreate(savedInstanceState)
        mAmap = mMapView.map
        mAmap.uiSettings.isZoomControlsEnabled = false
    }

    fun checkPermission() {
        RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe { granted ->
                    if (!granted) {
                        toast(R.string.disagree_permission)
                    }
                }


    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvMine -> {
                startActivity<AccountSettingActivity>()
            }
            R.id.mIvProtocol -> {
                startActivity<CustomerServiceActivity>()
            }
            R.id.mIvAbout -> {
                startActivity<AboutActivity>()
            }
            R.id.mIvLocation -> {
                toCurrentLocation()
            }
            R.id.mIvRefresh -> {

                toRefresh()

            }
            R.id.mTvBorrow -> {
                startActivity<ScanActivity>()
            }
            R.id.mTvRepay -> {
                startActivity<ScanActivity>()
            }
            R.id.mTvToWallet -> {
                startActivity<MyWalletActivity>()
            }
            R.id.mTvToUseRecord -> {
                startActivity<BorrowRecordActivity>()
            }
            R.id.mTvToMsg -> {
                startActivity<MsgActivity>()
            }
            R.id.mTvToCredits -> {
                startActivity<CreditsActivity>()
            }
            else -> {
            }
        }

    }

    /**
     * 重新加載數據
     */
    private fun toRefresh() {


    }

    /**
     * 定位到當前位置(真實位置)
     */
    private fun toCurrentLocation() {


    }

    private var mExitTime = 0L
    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime < 2000) {
            finish()
        } else {
            toast(getString(R.string.twice_click_to_exit))
            mExitTime = System.currentTimeMillis()
        }
    }


    /**
     * marker  跳一跳
     */
    fun jumpPoint(marker: Marker) {
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val proj = mAmap.projection
        val markerLatlng = marker.position
        val markerPoint = proj.toScreenLocation(markerLatlng)
        markerPoint.offset(0, -100)
        val startLatLng = proj.fromScreenLocation(markerPoint)
        val duration: Long = 1500

        val interpolator = BounceInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val lng = t * markerLatlng.longitude + (1 - t) * startLatLng.longitude
                val lat = t * markerLatlng.latitude + (1 - t) * startLatLng.latitude
                marker.position = LatLng(lat, lng)
                if (t < 1.0) {
                    handler.postDelayed(this, 16)
                }
            }
        })
    }
}
