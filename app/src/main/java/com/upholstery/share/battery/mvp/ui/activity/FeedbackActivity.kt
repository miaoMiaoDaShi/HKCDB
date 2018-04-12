package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import cn.zcoder.xxp.base.app.GlideImageEngine
import cn.zcoder.xxp.base.ext.*
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.getRealFilePath
import com.upholstery.share.battery.mvp.modle.entity.UploadImageResponse
import com.upholstery.share.battery.mvp.presenter.FeedbackPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.find


/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description : 意見反饋
 */
class FeedbackActivity : BaseMvpActivity<MvpView, FeedbackPresenter>(), View.OnClickListener {

    /**
     * 上传成功后图片的路径
     */
    private var mImageFilePath = ""
    private val REQUEST_CODE_CHOOSE = 0x11
    private val mSelectedImages by lazy {
        mutableListOf<Uri>()
    }
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_feedback

    override fun showLoading(type: Int) = showDialog(mLoadingDialog)

    override fun dismissLoading(type: Int) = dismissDialog(mLoadingDialog)

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {
                toast(R.string.upload_image_file_error)
                mIvImage.setImageResource(R.drawable.ic_add_image)
            }
            else -> {
                toast(R.string.commit_failed)
            }
        }

    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {
                toast(R.string.upload_image_file_success)
                mImageFilePath = (data as UploadImageResponse).data.img
            }
            else -> {
                toast(R.string.commit_success)
                finish()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvImage -> {
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
            else -> {
                getPresenter().feedback(mImageFilePath, mEtContent.text.toString(), 0x11)
            }
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.feedback)
                .setOnLeftImageListener { onBackPressed() }
    }

    override fun bindListener() {
        super.bindListener()
        mBtnCommit.onClick(this)
        mIvImage.onClick(this)
    }

    override fun createPresenter(): FeedbackPresenter = FeedbackPresenter()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            data?.let {
                mSelectedImages.clear()
                mSelectedImages.addAll(Matisse.obtainResult(data))
                mIvImage.load(Matisse.obtainResult(data)[0])
                val imageFilePath = "${mSelectedImages[0].getRealFilePath(applicationContext)}"
                getPresenter().uploadImageFile(imageFilePath, 0x10)
            }


        }
    }
}