package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.zcoder.xxp.base.ext.onClick
import cn.zcoder.xxp.base.ext.toast
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.JsonBean
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.upholstery.share.battery.utils.GetJsonDataUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_city.*
import org.json.JSONArray
import java.util.ArrayList


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/2
 * Description : 修改居住城市
 */

class ChangeCityActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_change_city
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        findViewById<ToolBar>(R.id.mToolBar)
                .setTitle(R.string.in_city)
                .showRightText(true)
                .setOnLeftImageListener { finish() }
                .setOnRightTextListener(getString(R.string.save), { save() })

        mTvCity.text = intent.getStringExtra("city")
    }

    override fun start() {
        super.start()

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

    private val options1Items = ArrayList<JsonBean>()
    private val options2Items = ArrayList<ArrayList<String>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<String>>>()
    override fun bindListener() {
        super.bindListener()
        mTvCity.onClick {
            loadPickerViewData()

        }
    }

    private fun loadPickerViewData() {
        options1Items.clear()
        options2Items.clear()
        options3Items.clear()
        val cityPickerView = OptionsPickerBuilder(this,
                OnOptionsSelectListener { options1, options2, options3, v ->
                    run {
                        mTvCity.text = options1Items[options1].pickerViewText +
                                options2Items[options1][options2] +
                                options3Items[options1][options2][options3]
                    }
                })
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.confirm))
                .setLabels(getString(R.string.province), getString(R.string.city), getString(R.string.district))
                .build<Any>()


            Observable.create(object : ObservableOnSubscribe<List<JsonBean>> {                override fun subscribe(e: ObservableEmitter<List<JsonBean>>) {
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

    private fun save() {
        val intent = Intent()
        intent.putExtra("city", mTvCity.text.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}