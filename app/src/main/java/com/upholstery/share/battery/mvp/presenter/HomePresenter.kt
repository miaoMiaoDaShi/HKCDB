package com.upholstery.share.battery.mvp.presenter

import cn.zcoder.xxp.base.mvp.ui.MvpView

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/4
 * Description : 首页
 */
class HomePresenter : UsingRecordPresenter() {
    private val mNearTheSitePresenter by lazy {
        NearTheSitePresenter()
    }

    /**
     * 获取商家列表
     */
    fun getNearTheSites(lat: String, lng: String, distance: String,type: Int) {
      mNearTheSitePresenter.getNearTheSites(lat, lng, distance, type)
    }

    override fun detachView() {
        super.detachView()
        mNearTheSitePresenter.detachView()
    }

    override fun attachView(view: MvpView) {
        super.attachView(view)
        mNearTheSitePresenter.attachView(getView())
    }
}