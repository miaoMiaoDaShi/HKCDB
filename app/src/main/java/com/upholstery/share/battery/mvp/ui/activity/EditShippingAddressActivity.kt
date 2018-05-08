package com.upholstery.share.battery.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.showDialog
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.MvpView
import cn.zcoder.xxp.base.mvp.ui.activity.BaseMvpActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.JsonBean
import com.upholstery.share.battery.mvp.modle.entity.ShippingAddressListResponse
import com.upholstery.share.battery.mvp.presenter.ShippingAddressPresenter
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.upholstery.share.battery.utils.GetJsonDataUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.edit_shipping_address_detail.*
import org.jetbrains.anko.find
import org.json.JSONArray
import java.util.ArrayList
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/5/7
 * Description : 新增("0x10) 编辑(0x11)  收货地址
 */
class EditShippingAddressActivity : BaseMvpActivity<MvpView, ShippingAddressPresenter>() {
    private val mLoadDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.edit_shipping_address_detail

    override fun showLoading(type: Int) {
        showDialog(mLoadDialog)
    }

    override fun dismissLoading(type: Int) {
        cn.zcoder.xxp.base.ext.dismissDialog(mLoadDialog)
    }

    override fun handlerError(type: Int, e: String) {
        when (type) {
            0x10 -> {//新增
                toast(R.string.add_failed)
            }
            0x11 -> {//编辑
                toast(R.string.mod_failed)
            }
            else -> {
            }
        }
    }

    override fun handlerSuccess(type: Int, data: Any) {
        when (type) {
            0x10 -> {//新增
                toast(R.string.add_success)
                finish()
            }
            0x11 -> {//编辑
                toast(R.string.mod_success)
                finish()
            }
            else -> {
            }
        }
    }

    private var mData: ShippingAddressListResponse.DataBean? = null
    private val mType by lazy {
        intent.getIntExtra("type", 0x10)
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        super.initData()

        when (mType) {
            0x10 -> {

            }
            0x11 -> {
                mData = intent.getSerializableExtra("data") as ShippingAddressListResponse.DataBean
                mData?.let {
                    mEtShippingReceiveName.setText(it.linkman)
                    mEtPhone.setText(it.phone)
                    mEtDetailAddress.setText(it.address)
                    mEtEmail.setText(it.postCode)
                    mCurrentSelectAddress = "${it.province};${it.city};${it.area}"
                    mTvSimpleAddress.text = it.province + it.city + it.area
                }

            }
            else -> {

            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar)
                .setTitle(if (mType == 0x10) R.string.add_shipping_address else R.string.edit_shipping_address)
                .setOnLeftImageListener { finish() }

        mBtnConfirm.text = if (mType == 0x10) getString(R.string.save_and_use) else getString(R.string.save)
    }

    private val options1Items = ArrayList<JsonBean>()
    private val options2Items = ArrayList<ArrayList<String>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<String>>>()
    override fun bindListener() {
        super.bindListener()
        mTvSimpleAddress.onClick {
            closeKeyboard()
            loadPickerViewData()
        }
        mBtnConfirm.onClick {
            if (mType == 0x10) {//添加操作  需要額外 調用設為默認
                if (mCurrentSelectAddress.isEmpty()) {
                    toast(R.string.please_select_address)
                    return@onClick
                }
                getPresenter().addAddress(
                        linkman = mEtShippingReceiveName.text.toString().trim(),
                        phone = mEtPhone.text.toString().trim(),
                        address = mEtDetailAddress.text.toString(),
                        postCode = mEtEmail.text.toString(),
                        province = mCurrentSelectAddress.split(";")[0],
                        city = mCurrentSelectAddress.split(";")[1],
                        area = mCurrentSelectAddress.split(";")[2],
                        type = 0x10
                )
            } else if (mType == 0x11) {//編輯操作  直接保存
                mData?.let {
                    getPresenter().editAddress(
                            id = it.id.toString(),
                            linkman = mEtShippingReceiveName.text.toString().trim(),
                            phone = mEtPhone.text.toString().trim(),
                            address = mEtDetailAddress.text.toString(),
                            postCode = mEtEmail.text.toString(),
                            province = mCurrentSelectAddress.split(";")[0],
                            city = mCurrentSelectAddress.split(";")[1],
                            area = mCurrentSelectAddress.split(";")[2],
                            type = 0x11
                    )
                }
            }
        }
    }

    private var mCurrentSelectAddress = ""
    @SuppressLint("SetTextI18n")
    private fun loadPickerViewData() {
        options1Items.clear()
        options2Items.clear()
        options3Items.clear()
        val cityPickerView = OptionsPickerBuilder(this,
                OnOptionsSelectListener { options1, options2, options3, v ->
                    run {
                        mCurrentSelectAddress = "${options1Items[options1].pickerViewText};" +
                                "${options2Items[options1][options2]};${options3Items[options1][options2][options3]}"
                        mTvSimpleAddress.text = mCurrentSelectAddress.replace(";", "")

                    }
                })
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.confirm))
                .setLabels(getString(R.string.province), getString(R.string.city), getString(R.string.district))
                .build<Any>()


        Observable.create(object : ObservableOnSubscribe<List<JsonBean>> {
            override fun subscribe(e: ObservableEmitter<List<JsonBean>>) {
                try {
                    val JsonData = GetJsonDataUtil().getJson(applicationContext, "province.json")//获取assets目录下的json文件数据
                    val jsonBean = parseData(JsonData)//用Gson 转成实体
                    e.onNext(jsonBean)
                } catch (s: Exception) {
                    e.onError(Throwable(s))
                }
                e.onComplete()
            }


        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    options1Items.addAll(it)
                    for (i in it.indices) {//遍历省份
                        val CityList = ArrayList<String>()//该省的城市列表（第二级）
                        val Province_AreaList = ArrayList<ArrayList<String>>()//该省的所有地区列表（第三极）

                        for (c in 0 until it.get(i).getCityList().size) {//遍历该省份的所有城市
                            val CityName = it.get(i).getCityList().get(c).getName()
                            CityList.add(CityName)//添加城市
                            val City_AreaList = ArrayList<String>()//该城市的所有地区列表

                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (it.get(i).getCityList().get(c).getArea() == null || it.get(i).getCityList().get(c).getArea().size === 0) {
                                City_AreaList.add("")
                            } else {
                                City_AreaList.addAll(it.get(i).getCityList().get(c).getArea())
                            }
                            Province_AreaList.add(City_AreaList)//添加该省所有地区数据
                        }

                        /**
                         * 添加城市数据
                         */
                        options2Items.add(CityList)
                        /**
                         * 添加地区数据
                         */
                        options3Items.add(Province_AreaList)

                        cityPickerView.setPicker(options1Items as List<Any>?, options2Items as List<List<Any>>?, options3Items as List<List<List<Any>>>?)
                        cityPickerView.show()
                    }
                }

    }

    fun parseData(result: String): ArrayList<JsonBean> {//Gson 解析
        val detail = ArrayList<JsonBean>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson<JsonBean>(data.optJSONObject(i).toString(), JsonBean::class.java)
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return detail
    }

    private fun closeKeyboard() {
        val view = window.peekDecorView()
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun createPresenter(): ShippingAddressPresenter = ShippingAddressPresenter()
}