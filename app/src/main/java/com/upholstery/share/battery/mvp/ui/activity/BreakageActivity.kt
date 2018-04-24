package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.app.GlideImageEngine
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.getRealFilePath
import com.upholstery.share.battery.mvp.modle.entity.UploadImageResponse
import com.upholstery.share.battery.mvp.presenter.UsePresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_breakage.*
import org.jetbrains.anko.find
import timber.log.Timber

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/17
 * Description : 设备报损
 */
class BreakageActivity : BaseMvpActivity<MvpView, UsePresenter>(), View.OnClickListener {
    private val REQUEST_CODE_CHOOSE = 0x11
    private var mImageUrl = ""
    private val mSelectedImages by lazy {
        mutableListOf<Uri>()
    }
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.breakage)
                .setOnLeftImageListener { finish() }
    }
    override fun getLayoutId(): Int = R.layout.activity_breakage

    override fun showLoading(type: Int) {
        showDialog(mLoadingDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                mImageUrl = ""
                toast(R.string.upload_image_file_error)
            }
            0x11 -> {
                toast(R.string.commit_failed)
            }
            0x12 -> {
                toast(R.string.content_not_be_null)
            }
            else -> {
            }
        }

    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                mImageUrl = (data as UploadImageResponse).data.img
                toast(R.string.upload_image_file_success)
                mIvImageContent.load(mImageUrl)
            }
            0x11 -> {
                toast(R.string.commit_success)
                finish()
            }
            else -> {
            }
        }
    }

    override fun createPresenter(): UsePresenter = UsePresenter()
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvImageContent -> {
                RxPermissions(this)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe({
                            if (it) {
                                Matisse.from(this)
                                        .choose(MimeType.allOf())
                                        .countable(true)
                                        .capture(true)
                                        .captureStrategy(CaptureStrategy(true, "com.upholstery.share.battery.fileprovider"))
                                        .maxSelectable(1)
                                        .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                        .thumbnailScale(0.85f)
                                        .imageEngine(GlideImageEngine())
                                        .forResult(REQUEST_CODE_CHOOSE)
                            } else {
                                toast(R.string.please_authorization)
                            }
                        })
            }
            R.id.mBtnCommit -> {
                getPresenter().breakageDevice(mSno, mEtContent.text.toString().trim(), mOrderNo, mImageUrl, 0x11)
            }
            else -> {
            }
        }

    }

    override fun bindListener() {
        super.bindListener()
        mIvImageContent.onClick(this)
        mBtnCommit.onClick(this)
    }

    private var mSno = ""
    private var mOrderNo = ""
    override fun initData() {
        super.initData()
        mSno = intent.getStringExtra("sno")
        mOrderNo = intent.getStringExtra("orderNo")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            data?.let {
                mSelectedImages.clear()
                mSelectedImages.addAll(Matisse.obtainResult(data))
                //mIvImage.load(Matisse.obtainResult(data)[0])
                val imageFilePath = "${mSelectedImages[0].getRealFilePath(applicationContext)}"
                Timber.i(imageFilePath)
                getPresenter().uploadImageFile(imageFilePath, 0x10)
            }

        }
    }


}