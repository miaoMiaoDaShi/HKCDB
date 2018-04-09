package com.upholstery.share.battery.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.zcoder.xxp.base.ext.load
import cn.zcoder.xxp.base.mvp.ui.fragment.BaseFragment
import com.upholstery.share.battery.R
import kotlinx.android.synthetic.main.fragment_guide_common_page.*


/**
 * Author : zhongwenpeng
 * Email : 1340751953@qq.com
 * Time :  2018/3/7
 * Description :
 */

class GuideCommonPageFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_guide_common_page
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mIvGuideImage.load(arguments.getString("url"))
    }

    companion object {
        fun newInstance(url: String): Fragment {
            val bundle = Bundle()
            bundle.putString("url", url)
            val fragment = GuideCommonPageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}