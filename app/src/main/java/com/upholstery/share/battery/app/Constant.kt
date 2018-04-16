package com.upholstery.share.battery.app

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
class Constant {
    companion object {
        /**
         * 用戶信息
         */
        val KEY_USER_INFO = "userInfo"
        /**
         * 用户详细信息
         */
        val KEY_USER_DETAIL_INFO = "userDetailInfo"
        /**
         * 第一次進入
         */
        val KEY_IS_FIRST_ENTER = "isFirstEnter"

        /**
         * 加载的范围
         */
        val LOAD_NEAR_THE_SITES_RANGE = "5"

        /**
         * stripe相关
         */
        val STRIPE_CUSTOMER_NAME = "Paul Kei"//name
        val STRIPE_CUSTOMER_EMAIL = "paul.kei@gaadme.com"//email
        val STRIPE_REDIRECT_ADDRESS = "upholstery://alipay"//回到activity的地址
        val STRIPE_PUBLISHABLE_KEY = "pk_live_JiZpMtKEH6KvTbWEBkf8B9ZA"//回到activity的地址
    }
}


