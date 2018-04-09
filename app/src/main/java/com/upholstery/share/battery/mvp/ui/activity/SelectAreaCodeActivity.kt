package com.upholstery.share.battery.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.zcoder.xxp.base.mvp.ui.activity.BaseActivity
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import com.upholstery.share.battery.R
import com.upholstery.share.battery.mvp.modle.entity.AreaCodeInfo
import com.upholstery.share.battery.mvp.ui.dialog.LoadingDialog
import com.upholstery.share.battery.mvp.ui.widgets.ToolBar
import com.upholstery.share.battery.utils.CharacterParser
import kotlinx.android.synthetic.main.activity_select_area_code.*
import org.jetbrains.anko.find

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/4/2
 * Description :
 */
class SelectAreaCodeActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener, OnQuickSideBarTouchListener {

    override fun onLetterTouching(touching: Boolean) {


        //可以自己加入动画效果渐显渐隐
        mQuickSideBarTipsView.setVisibility(if (touching) View.VISIBLE else View.INVISIBLE)

    }

    override fun onLetterChanged(letter: String?, position: Int, y: Float) {
        mQuickSideBarTipsView.setText(letter, position, y)
        //有此key则获取位置并滚动到该位置
        if (mLetters.containsKey(letter)) {
            mRvSelectAreaCode.scrollToPosition(mLetters[letter]!!)
        }
    }

    val mLetters = HashMap<String, Int>()
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val areaCodeInfo = adapter.data[position] as AreaCodeInfo
        setResult(Activity.RESULT_OK, intent.putExtra("areaName", areaCodeInfo.areaName)
                .putExtra("areaCode", areaCodeInfo.areaCode))
        onBackPressed()

    }

    private lateinit var mAdapter: BaseQuickAdapter<AreaCodeInfo, BaseViewHolder>
    private val mLoadingDialog by lazy {
        LoadingDialog.getInstance(supportFragmentManager)
    }

    override fun getLayoutId(): Int = R.layout.activity_select_area_code


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        find<ToolBar>(R.id.mToolBar).setTitle(R.string.select_area_code).setOnLeftImageListener { onBackPressed() }
        mRvSelectAreaCode.layoutManager = LinearLayoutManager(applicationContext)

        mAdapter = object : BaseQuickAdapter<AreaCodeInfo, BaseViewHolder>(R.layout.recycler_select_area_code),
                StickyRecyclerHeadersAdapter<BaseViewHolder> {
            override fun onCreateHeaderViewHolder(parent: ViewGroup): BaseViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_select_area_code_head, parent, false)

                return BaseViewHolder(view)
            }

            override fun onBindHeaderViewHolder(holder: BaseViewHolder, position: Int) {
                holder.setText(R.id.mTvHead, data[position].sortKey)
            }

            override fun getHeaderId(position: Int): Long = (getItem(position) as AreaCodeInfo).sortKey[0].toLong()

            override fun convert(helper: BaseViewHolder, item: AreaCodeInfo) {
                helper.setText(R.id.mTvAreaName, item.areaName)
                        .setText(R.id.mTvAreaCode, "+${item.areaCode}")
            }

        }
        mAdapter.onItemClickListener = this

        mRvSelectAreaCode.adapter = mAdapter

        //获取资源文件的资源
        val areaDatas = resources.getStringArray(R.array.cities_data)
        //原始的areaCodeInfoList
        val areaCodeInfos = ArrayList<AreaCodeInfo>(areaDatas.size)
        //转索引
        val customLetters = ArrayList<String>()
        var position = 0
        areaDatas.forEach {
            val areaName = it.split("+")[0]
            val areaCode = it.split("+")[1]
            val sortKey = getSortKey(areaName)
            areaCodeInfos.add(AreaCodeInfo(areaName, areaCode, sortKey))

            if ((!customLetters.contains(sortKey))) {
                customLetters.add(sortKey)
                mLetters[sortKey] = position
            }
            position++
        }

        mQuickSideBarView.letters = customLetters
        mQuickSideBarView.setOnQuickSideBarTouchListener(this)
        mAdapter.replaceData(areaCodeInfos)

        val headersDecor = StickyRecyclerHeadersDecoration(mAdapter as StickyRecyclerHeadersAdapter<*>)

        mRvSelectAreaCode.addItemDecoration(headersDecor)
    }

    /**
     * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
     *
     * @param sortKeyString 数据库中读取出的sort key
     * @return 英文字母或者#
     */
    private fun getSortKey(sortKeyString: String): String {


        val pinyin = CharacterParser.getInstance().getSelling(sortKeyString)

        val key = pinyin.substring(0, 1).toUpperCase()
        return if (key.matches("[A-Z]".toRegex())) {
            key
        } else "#"
    }
}