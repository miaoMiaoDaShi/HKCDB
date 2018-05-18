package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
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
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.mvp.modle.entity.NearTheSitesResponse
import com.upholstery.share.battery.mvp.modle.entity.UserDetailResponse
import com.upholstery.share.battery.mvp.modle.entity.UsingOrderResponse
import com.upholstery.share.battery.mvp.presenter.HomePresenter
import com.upholstery.share.battery.mvp.presenter.ModPersonalDataPresenter
import com.upholstery.share.battery.mvp.ui.dialog.SiteDetailPop
import com.upholstery.share.battery.utils.MapUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.location.LocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.upholstery.share.battery.mvp.ui.dialog.TipDialog
import com.upholstery.share.battery.mvp.ui.dialog.WarningTipsDialog
import timber.log.Timber


/**
 *
 */
class MainActivity : BaseMvpActivity<MvpView, HomePresenter>(), View.OnClickListener,
        OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener {


    private var mGoogleMap: GoogleMap? = null
    private val mFusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this@MainActivity)
    }
    private val REQUEST_CHECK_SETTINGS = 0x10

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        mGoogleMap = p0?.apply {
            //界面配置
            //调整缩放
            animateCamera(CameraUpdateFactory.zoomBy(13f))

            //去除一些没卵用的按钮
            with(uiSettings) {
                isZoomControlsEnabled = false
                isMyLocationButtonEnabled = false
            }
            //开启定位图城
            isMyLocationEnabled = true
            setOnCameraIdleListener(this@MainActivity)
            setOnMarkerClickListener(this@MainActivity)

            //定位请求发起(检测)
            val locationRequest = createLocationRequest()
            LocationServices.getSettingsClient(this@MainActivity)
                    .checkLocationSettings(
                            LocationSettingsRequest.Builder()
                                    .addLocationRequest(locationRequest)
                                    .build())
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener { exception ->
                        if (exception is ResolvableApiException){
                            try {
                                exception.startResolutionForResult(this@MainActivity,
                                        REQUEST_CHECK_SETTINGS)
                            } catch (sendEx: IntentSender.SendIntentException) {
                            }
                        }
                    }



            //请求的发起
            mFusedLocationClient.requestLocationUpdates(locationRequest,object:LocationCallback(){
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    p0?.let {
                        mLocation = it.lastLocation
                        Timber.i("接收到位置: 经度: ${it.lastLocation.longitude} 维度${it.lastLocation.latitude}")
                        if (mIsFrist) {
                            mIsFrist = false
                            val latLng = LatLng(it.lastLocation.latitude, it.lastLocation.longitude)
                            mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                            loadNearTheSitesByLocation(latLng)
                        }

                    }
                }

                override fun onLocationAvailability(p0: LocationAvailability?) {
                    super.onLocationAvailability(p0)
                }
            },null)

        }



    }

    /**
     * 定位请求相关配置
     */
    fun createLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onCameraIdle() {
        calculateCenterLocation()
    }


    private var mCurrentZoom = 0f


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
        mGoogleMap?.apply {
            val projection = projection
            val centerLatLng = projection.fromScreenLocation(
                    Point(mIvCenterLocation.x.toInt(), mIvCenterLocation.y.toInt()))

            loadNearTheSitesByLocation(centerLatLng)
        }

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
            0x14, 0x15 -> toast(R.string.check_order_ing)
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

    /**
     * 餘額不足  提示充值
     */
    private val mWarningDialog by lazy {
        WarningTipsDialog.newInstance(getString(R.string.not_sufficient_funds), getString(R.string.i_know))
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
                if (data.data.point >= 50) {
                    getPresenter().getUsingRecord(0x14)
                } else {
                    showDialog(mWarningDialog)
                }
            }
            0x13 -> {
                data as UserDetailResponse
                if (data.data.point >= 50) {
                    getPresenter().getUsingRecord(0x15)
                } else {
                    showDialog(mWarningDialog)
                }
            }
            0x14 -> {
                //借还状态：0-初始化 1-使用中 2-待支付  3-已完成  4-报失 5-报损',
                data as UsingOrderResponse
                data.data?.let {
                    mTvUsing.visible(data.data.statusX == 1)
                    if (data.data.statusX == 1) {
                        toast(R.string.have_ing_order)
                        return
                    }
                }
                //showDialog(mWarningDialog)
                startActivity<ScanActivity>("type" to 0x10)
            }
            0x15 -> {
                //借还状态：0-初始化 1-使用中 2-待支付  3-已完成  4-报失 5-报损',
                data as UsingOrderResponse
                data.data?.let {
                    mTvUsing.visible(data.data.statusX == 1)
                    startActivity<ScanActivity>("type" to 0x11)
                    return
                }
                toast(R.string.not_ing_order)

            }
            else -> {

            }
        }
    }

    /**
     * 添加商家信息到地图上
     */
    private fun setupMarkerToMap(data: List<NearTheSitesResponse.DataBean>) {
        mGoogleMap?.run {
            clear()
            mLocation?.let { location->
                data.forEach {
                    //計算距離
                    it.distance = MapUtils
                            .calculateLineDistance(LatLng(location.latitude, location.longitude),
                                    LatLng(it.lat, it.lng))

                    mMarkerData[addMarker(MarkerOptions()
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.ic_merchant))
                            .position(LatLng(it.lat, it.lng))).id] = it
                }
            }

        }


    }

    private val mModPersonalDataPresenter by lazy {
        ModPersonalDataPresenter()
    }

    override fun createPresenter(): HomePresenter {
        mModPersonalDataPresenter.attachView(this)
        return HomePresenter()
    }


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
        mTvToMy.onClick(this)
        mTvToUseRecord.onClick(this)
        mTvToMsg.onClick(this)
        mTvToShop.onClick(this)
        mTvToCredits.onClick(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        checkPermission(savedInstanceState)
        //餘額不住 提示,點擊進入充值頁面
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
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mMapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * 定位  回调
     */
    private var mIsFrist = true//第一次???
    /**
     * 當前位置
     */
    private var mLocation: Location ?= null

//    override fun onMyLocationChange(p0: Location) {
//        mLocation = p0
//        //第一次 位移到当前位置
//        if (mIsFrist) {
//            mIsFrist = false
//            val latLng = LatLng(p0.latitude, p0.longitude)
//            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//            loadNearTheSitesByLocation(latLng)
//        }
//    }

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
            R.id.mTvToMy -> {
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
                    val projection = mGoogleMap!!.projection
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
                mModPersonalDataPresenter.getUserDetail(0x12)
            }
            R.id.mTvRepay -> {
                mModPersonalDataPresenter.getUserDetail(0x13)
            }
//            R.id.mTvToWallet -> {
//                startActivity<MyWalletActivity>()
//            }
            R.id.mTvToUseRecord -> {
                startActivity<BorrowRecordActivity>()
            }
            R.id.mTvToMsg -> {
                startActivity<MsgActivity>()
            }
            R.id.mTvToCredits -> {
                startActivity<CreditsAndCurrencyActivity>()
            }
            R.id.mTvToShop -> {
                TipDialog.show("商城即將開放,敬請期待吧", supportFragmentManager)
                //startActivity<ShopActivity>()
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
            val latLng = LatLng(it.latitude, it.longitude)
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


    }



