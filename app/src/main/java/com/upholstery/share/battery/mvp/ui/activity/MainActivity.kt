package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import android.view.animation.BounceInterpolator
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.showSnackBar
import cn.zcoder.xxp.base.ext.visible
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.NearTheSitesResponse
import com.upholstery.share.battery.mvp.modle.entity.UserDetailResponse
import com.upholstery.share.battery.mvp.modle.entity.UsingOrderResponse
import com.upholstery.share.battery.mvp.presenter.HomePresenter
import com.upholstery.share.battery.mvp.presenter.ModPersonalDataPresenter
import com.upholstery.share.battery.mvp.ui.dialog.SiteDetailPop
import com.upholstery.share.battery.mvp.ui.dialog.WarningDialog
import com.upholstery.share.battery.utils.MapUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.location.LocationManager


/**
 *
 */
class MainActivity : BaseMvpActivity<MvpView, HomePresenter>(), View.OnClickListener,
        AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener, AMap.OnCameraChangeListener {
    override fun onCameraChangeFinish(p0: CameraPosition?) {
        if (p0 != null) return
        calculateCenterLocation()
//        p0?.let {
//            if (mCurrentZoom != it.zoom) {
//                mCurrentZoom = it.zoom
//            } else {
//
//            }
//        }

    }

    private var mCurrentZoom = 0f
    override fun onCameraChange(p0: CameraPosition?) {
    }
//    override fun onCameraChange(p0: CameraPosition?) {
//
//        p0?.let {
//            if (it.zoom != mMapZoom) {
//                mMapZoom = it.zoom
//                calculateCenterLocation()
//            }
//        }
//    }
//
//    var mMapZoom = 0f
//    override fun onCameraChangeFinish(p0: CameraPosition?) {
//
//
//    }

    override fun onMarkerClick(p0: Marker): Boolean {

        mMarkerData[p0.id]?.let {
            SiteDetailPop(it, this)
                    .showAtLocation(find(R.id.mTvBorrow),
                            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
        }

        return true
    }

    /**
     * marker 和 data两两对应
     */
    private val mMarkerData = HashMap<String, NearTheSitesResponse.DataBean>()


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
            0x12, 0x13 -> {
                toast(R.string.load_money_info_error)
            }
            else -> {

            }
        }
    }

    private val mWarningDialog by lazy {
        WarningDialog()
    }

    private var mHaveOrder = false
    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                if ((data as NearTheSitesResponse).data.isEmpty()) {
                    showSnackBar(R.string.no_data_mod_location, Snackbar.LENGTH_LONG)
                    return
                }

                setupMarkerToMap(data.data)


            }
            0x11 -> {
                //借还状态：0-初始化 1-使用中 2-待支付  3-已完成  4-报失 5-报损',
                data as UsingOrderResponse

                mTvUsing.visible(data.data.statusX == 1)
                if (data.data.statusX == 1) {
                    mHaveOrder = true
                }
            }
            0x12 -> {
                data as UserDetailResponse
                if (data.data.money > 0) {
                    startActivity<ScanActivity>("type" to 0x10)
                } else {
                    showDialog(mWarningDialog)
                }
            }
            0x13 -> {
                data as UserDetailResponse
                if (data.data.money > 0) {
                    startActivity<ScanActivity>("type" to 0x11)
                } else {
                    showDialog(mWarningDialog)
                }
            }
            else -> {

            }
        }
    }

    /**
     * 添加商家信息到地图上
     */
    private fun setupMarkerToMap(data: List<NearTheSitesResponse.DataBean>) {
        mAmap.clear()



        data.forEach {
            //計算距離
            it.distance = MapUtils
                    .calculateLineDistance(LatLng(mLocation.latitude, mLocation.longitude),
                            LatLng(it.lat, it.lng))

            mMarkerData[mAmap.addMarker(MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_merchant))
                    .position(LatLng(it.lat, it.lng))).id] = it
        }


    }

    private val mModPersonalDataPresenter by lazy {
        ModPersonalDataPresenter()
    }

    override fun createPresenter(): HomePresenter {
        mModPersonalDataPresenter.attachView(this)
        return HomePresenter()
    }

    lateinit var mAmap: AMap

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun bindListener() {
        super.bindListener()
        mIvMine.onClick(this)
        mIvProtocol.onClick(this)
        mIvAbout.onClick(this)
        mIvToSite.onClick(this)
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
                        if (isOpen(applicationContext)) {

                            initMap(savedInstanceState)
                        } else {
                            toast("请打开GPS")
                        }
                    }
                }


    }

    fun isOpen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (gps || network) {
            true
        } else false

    }

    /**
     * 地图的初始化操作 ,,以及预先的定位
     */
    private var mIsMove = false

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
        mAmap.setOnCameraChangeListener(this)
//        mAmap.setOnMapTouchListener { event ->
//            when (event.action) {
//                MotionEvent.ACTION_MOVE -> {
//                    mIsMove = true
//                }
//                MotionEvent.ACTION_UP -> {
//                    if (mIsMove) {
//                        mIsMove = false
//                        calculateCenterLocation()
//                    }
//                }
//                else -> {
//                }
//            }
//            false
//        }
        mAmap.setOnMarkerClickListener(this)

    }

    /**
     * 定位  回调
     */
    private var mIsFrist = true//第一次???
    /**
     * 當前位置
     */
    private lateinit var mLocation: Location

    override fun onMyLocationChange(p0: Location) {
        mLocation = p0
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
        getPresenter().getUsingRecord(0x11)
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
        mModPersonalDataPresenter.detachView()
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
            R.id.mIvToSite -> {

                mLocation?.let {
                    val projection = mAmap.projection
                    val centerLatLng = projection.fromScreenLocation(
                            Point(mIvCenterLocation.x.toInt(), mIvCenterLocation.y.toInt()))
                    startActivity<NearTheSitesActivity>("lat" to centerLatLng.latitude,
                            "lng" to centerLatLng.longitude)
                }
            }
            R.id.mIvLocation -> {
                toCurrentLocation()
            }
            R.id.mIvRefresh -> {

                toRefresh()

            }
            R.id.mTvBorrow -> {
                if (mHaveOrder) {
                    toast(R.string.have_ing_order)
                    return
                }
                mModPersonalDataPresenter.getUserDetail(0x12)
            }
            R.id.mTvRepay -> {
                if (!mHaveOrder) {
                    toast(R.string.not_ing_order)
                    return
                }
                mModPersonalDataPresenter.getUserDetail(0x13)
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
        mLocation?.let {
            val latLng = LatLng(mLocation.latitude, mLocation.longitude)
            loadNearTheSitesByLocation(latLng)
        }

    }


    /**
     * 定位到當前位置(真實位置)
     */
    private fun toCurrentLocation() {
        mIsFrist = true
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
