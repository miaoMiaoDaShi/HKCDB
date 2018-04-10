package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MyLocationStyle
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.NearTheSitesResponse
import com.upholstery.share.battery.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *
 */
class MainActivity : BaseMvpActivity<MvpView, HomePresenter>(), View.OnClickListener,
        AMap.OnMyLocationChangeListener, AMap.OnMapTouchListener {

    private var mIsMove = false
    override fun onTouch(p0: MotionEvent) {
        when (p0.action) {
            MotionEvent.ACTION_MOVE -> {
                mIsMove = true

            }
            MotionEvent.ACTION_UP -> {
                if (mIsMove) {
                    calculateCenterLocation()
                    mIsMove = false
                }
            }
            else -> {
            }
        }
    }

    /**
     * 计算当前中心点  对应的经纬度
     */
    private fun calculateCenterLocation() {
        val projection = mAmap.projection
        val centerLatLng = projection.fromScreenLocation(
                Point(mIvCenterLocation.x.toInt(), mIvCenterLocation.y.toInt()))

        loadNearTheSitesByLocation(centerLatLng)
    }


    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

        }
    }

    override fun showLoading(type: Int) {
        when (type) {
        //加载附近商家数据ing
            0x10 -> {

            }
            else -> {
            }
        }

    }

    override fun dismissLoading(type: Int) {
        when (type) {
        //加载附近商家数据ing
            0x10 -> {

            }
            else -> {
            }
        }
    }


    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {

            }
            else -> {

            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                if ((data as NearTheSitesResponse).data.isEmpty()) {
                    showSnackBar(R.string.no_data_mod_location, Snackbar.LENGTH_LONG)
                }
            }
            else -> {

            }
        }
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
        checkPermission(savedInstanceState)

    }

    fun checkPermission(savedInstanceState: Bundle?) {
        RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted ->
                    if (!granted) {
                        toast(R.string.disagree_permission)
                    } else {
                        initMap(savedInstanceState)
                    }
                }


    }

    /**
     * 地图的初始化操作 ,,以及预先的定位
     */
    private fun initMap(savedInstanceState: Bundle?) {
        mMapView.onCreate(savedInstanceState)
        mAmap = mMapView.map
        mAmap.uiSettings.isZoomControlsEnabled = false

        //定位
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
        myLocationStyle.interval(2_000)
        mAmap.myLocationStyle = myLocationStyle
        mAmap.isMyLocationEnabled = true
        mAmap.setOnMyLocationChangeListener(this)
        mAmap.setOnMapTouchListener(this)

    }

    /**
     * 定位  回调
     */
    private var mIsFrist = true//第一次???

    override fun onMyLocationChange(p0: Location) {
        //第一次 位移到当前位置
        if (mIsFrist) {
            mIsFrist = false
            val latLng = LatLng(p0.latitude, p0.longitude)
            mAmap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            loadNearTheSitesByLocation(latLng)
        }
    }

    /**
     * 根据位置 加载数据
     */
    private fun loadNearTheSitesByLocation(latLng: LatLng) {
        getPresenter().getNearTheSites("${latLng.latitude}", "${latLng.longitude}",
                Constant.LOAD_NEAR_THE_SITES_RANGE, 0x10)
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
        val duration: Long = 1500
        val proj = mAmap.projection
        val markerLatlng = marker.position
        val markerPoint = proj.toScreenLocation(markerLatlng)
        markerPoint.offset(0, -100)
        val startLatLng = proj.fromScreenLocation(markerPoint)

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
