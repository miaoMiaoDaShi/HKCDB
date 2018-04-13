package com.upholstery.share.battery.mvp.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import cn.zcoder.xxp.base.app.GlideImageEngine
import cn.zcoder.xxp.base.app.Preference
import cn.zcoder.xxp.base.ext.*
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.blankj.utilcode.util.TimeUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.upholstery.share.battery.R
import com.upholstery.share.battery.app.Constant
import com.upholstery.share.battery.app.getRealFilePath
import com.upholstery.share.battery.mvp.modle.entity.UploadImageResponse
import com.upholstery.share.battery.mvp.modle.entity.UserDetailResponse
import com.upholstery.share.battery.mvp.presenter.ModPersonalDataPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_mine.*
import org.jetbrains.anko.startActivityForResult
import timber.log.Timber
import java.text.SimpleDateFormat


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description :
 *  9->  加載用戶的詳細信息
 *  0x10 -> 上传头像
 *  0x11 -> 修改头像
 *  0x12 -> 修改昵称
 *  0x13 -> 修改手机号
 *  0x14 -> 修改邮箱
 *  0x15 -> 修改性别
 *  0x16 -> 修改生日
 *  0x17 -> 修改城市
 */

class MineActivity : BaseMvpActivity<MvpView, ModPersonalDataPresenter>(), View.OnClickListener {
    override fun showLoading(type: Int) {
        if (type != 9) return
            showDialog(mLoadingDialog)


    }

    override fun dismissLoading(type: Int) {
        if (type != 9) return
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadingDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            9 -> {
                toast(R.string.load_error)
                onBackPressed()
            }
//            0x10 -> {
//            }
//            0x11 -> {
//
//            }
//            0x12 -> {
//
//            }
//            0x13 -> {
//
//            }
//            0x14 -> {
//
//            }
//            0x15 -> {
//
//            }
//            0x16 -> {
//
//            }
//            0x17 -> {
//
//            }
            else -> {
                toast(R.string.mod_failed)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            9 -> {

                (data as UserDetailResponse).data?.apply {
                    this.head?.let {
                        mIvPortrait.load(it)
                    }

                    mTvName.text = "${this.surname}${this.realname}"
                    mTvId.text = "${this.uid}"
                    mTvName1.text = "${this.surname}${this.realname}"
                    mTvPhone.text = "${this.phone}"
                    mTvEmail.text = this.email
                    mTvSex.text = if (this.sex == 0) "男" else "女"
                    mTvBirthday.text = TimeUtils.millis2String(this.birth, SimpleDateFormat(" yyyy-MM-dd"))
                    //mTvCity.text = this.deposit

                    mUserDetailInfo = toJson(this)
                }

            }
            0x10 -> {
                getPresenter().modPersonalData(mapOf("head" to  (data as UploadImageResponse).data.img), 0x11)
            }
            0x11, 0x12, 0x13, 0x14, 0x15, 0x16 -> {
                getPresenter().getUserDetail(9)
            }
//            0x11 -> {
//
//            }
//            0x12 -> {
//
//            }
//            0x13 -> {
//
//            }
//            0x14 -> {
//
//            }
//            0x15 -> {
//
//            }
//            0x16 -> {
//
//            }
//            0x17 -> {
//
//            }
            else -> {
                toast(R.string.mod_success)
            }
        }
    }

    override fun createPresenter(): ModPersonalDataPresenter = ModPersonalDataPresenter()

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
    private var mUserDetailInfo by Preference(Constant.KEY_USER_DETAIL_INFO, "")
    override fun bindListener() {
        super.bindListener()
        mIvPortrait.onClick(this)
        mTvName.onClick(this)
        mRlName.onClick(this)
        mRlPhone.onClick(this)
        mRlEmail.onClick(this)
        mRlSex.onClick(this)
        mRlBirthday.onClick(this)
        mRlCity.onClick(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvPortrait -> {
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
            R.id.mTvName -> {
                //startActivity<EditNicknameActivity>()
            }
            R.id.mRlName -> {
                //传入姓,名
                startActivityForResult<EditNicknameActivity>(0x12, "firstName" to
                        fromJson<UserDetailResponse>(mUserDetailInfo)?.data?.surname,
                        "lastName" to
                                fromJson<UserDetailResponse>(mUserDetailInfo)?.data?.realname)
            }
            R.id.mRlPhone -> {
                startActivityForResult<ChangeBindPhoneNumActivity>(0x13)
            }
            R.id.mRlEmail -> {
                startActivityForResult<ChangeEmailAddrsActivity>(0x14, "email" to
                        fromJson<UserDetailResponse>(mUserDetailInfo)?.data?.email)
            }
            R.id.mRlSex -> {
                startActivityForResult<ChangeSexActivity>(0x15)
            }
            R.id.mRlBirthday -> {
                startActivityForResult<ChangeBirthdayActivity>(0x16, "birthday" to
                        mTvBirthday.text.toString())
            }
            R.id.mRlCity -> {
                //startActivity<ChangeCityActivity>()
            }
            else -> {
            }
        }

    }

    override fun getLayoutId(): Int {

        return R.layout.activity_mine
    }

    override fun initData() {
        super.initData()
        getPresenter().getUserDetail(9)
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.mine_info)
                .setOnLeftImageListener { finish() }
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

        } else if (requestCode == 0x12 && resultCode == Activity.RESULT_OK) {//姓氏和名字
            data?.let {
                getPresenter().modPersonalData(mapOf("surname" to it.getStringExtra("firstName"),
                        "realname" to it.getStringExtra("lastName")), 0x12)

            }
        } else if (requestCode == 0x14 && resultCode == Activity.RESULT_OK) {
            data?.let { getPresenter().modPersonalData(mapOf("email" to it.getStringExtra("email")), 0x14) }
        } else if (requestCode == 0x15 && resultCode == Activity.RESULT_OK) {
            data?.let { getPresenter().modPersonalData(mapOf("sex" to it.getStringExtra("sex")), 0x15) }
        } else if (requestCode == 0x16 && resultCode == Activity.RESULT_OK) {
            data?.let { getPresenter().modPersonalData(mapOf("birth" to it.getStringExtra("birthday")), 0x16) }
        }
//        else if (requestCode == 0x17 && resultCode == Activity.RESULT_OK) {
//            data?.let { getPresenter().modPersonalData("", it.getStringExtra("value"), 0x17) }
//        }
    }

}