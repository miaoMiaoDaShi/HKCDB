package com.upholstery.share.battery.mvp.modle.api

import cn.zcoder.xxp.base.net.BaseResponse
import com.upholstery.share.battery.mvp.modle.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * Created by Zcoder
 * Email : 1340751953@qq.com
 * Time :  2018/3/31
 * Description :
 */
interface APIService {
    /**
     * 獲取驗證碼 1注册 2登陆 3重置密码 4，更换(绑定)手机号码
     */
    @Deprecated(message = "验证更改了", level = DeprecationLevel.ERROR)
    @GET("userController/phoneCode")
    fun getVerCode(@Query("areaCode") areaCode: String,
                   @Query("phone") phone: String,
                   @Query("used_to") userTo: String): Observable<BaseResponse>

    /**
     * 把手機號給後台
     */
    @GET("userController/appVerification")
    fun verPhone(@Query("phone") phone: String,
                 @Query("use_to") userTo: String): Observable<BaseResponse>

    /**
     * 登錄  1：账号，密码  2：uid,token
    3：第三方  4:手机号，证码

     */
    @POST("userController/login")
    @FormUrlEncoded
    fun login(@Field("login_type") type: String,
              @Field("open_type") openType: String?,
              @Field("account") account: String,
              @Field("password") password: String,
              @Field("surname") surName: String,
              @Field("realname") realname: String): Observable<UserResponse>

    /**
     * 注册
     */
    @POST("userController/userRegister")
    @FormUrlEncoded
    fun register(
            @Field("phone") phone: String,
            @Field("surname") surName: String,
            @Field("realname") realname: String,
            //  @Field("password") pwd: String,
            @Field("invitationCode") invitationCode: String,
            @Field("question") question: String = "7878"): Observable<BaseResponse>

    /**
     * 獲取邀請用戶列表
     */
    @GET("personController/myInvites")
    fun getInvite(@Query("page") page: Int): Observable<InvitesResponse>

    /**
     * 忘記用戶名
     */
    @POST("userController/forgetUserName")
    @FormUrlEncoded
    fun forgetUserName(@Field("phone") phone: String,
                       @Field("question") question: String): Observable<ForgetUserNameResponse>

    /**
     * 意見反饋
     */
    @POST("systemController/feedback")
    @FormUrlEncoded
    fun feedback(@Field("image") imagePath: String,
                 @Field("content") content: String): Observable<BaseResponse>

    /**
     * 个人账单
     */
    @GET("walletController/queryRecordMoney")
    fun getMyMoneyRecord(@Query("page") page: Int): Observable<MoneyRecordResponse>

    /**
     * 钱包信息
     */
    @GET("walletController/queryWallet")
    fun getWallet(): Observable<WalletResponse>

    /**
     * 充值
     * 支付方式 0-微信 1-信用卡（stripe支持支付宝支付）---pay_type
     * 0:充值 1:押金(默认为0)===>used_to
     * 这是信用卡strip支付需要传的东西—信用卡支付时此参数必须传 +==:>backToken
    默认为HKD，信用卡支付需要
     *
     *
     *  用于标识是公众号的请求还是app 的请求                  0--公众号请求
     * 1--app请求====>type

     */
    @POST("walletController/charge")
    @FormUrlEncoded
    fun topUpByAlipay(@Field("amount") amount: Int, @Field("pay_type") payType: Int = 1,
                      @Field("type") type: Int = 1

    ): Observable<TopUpResponse>

    /**
     * 微信支付
     */
    @POST("walletController/charge")
    @FormUrlEncoded
    fun topUpWeChat(@Field("amount") amount: Int, @Field("pay_type") payType: Int = 0,
                    @Field("type") type: Int = 1

    ): Observable<TopUpResponse>


    /**
     * 銀行卡充值
     */
    @POST("walletController/charge")
    @FormUrlEncoded
    fun topUpBankCard(@Field("amount") amount: Int, @Field("pay_type") payType: Int = 2,
                      @Field("backToken") backToken: String,
                      @Field("currency") currency: String, @Field("type") type: Int = 1

    ): Observable<TopUpResponse>

    /**
     * 银行卡列表
     */
    @GET("walletController/queryBank")
    fun getBankCard(): Observable<BankCardResponse>

    /**
     * 接一个设备
     */
    @POST("deviceController/borrowOne")
    @FormUrlEncoded
    fun borrowOne(@Field("sno") sno: String, @Field("nonce") nonce: String,
                  @Field("openId") openId: String? = null): Observable<BorrowOneResponse>

    /**
     * 开启仓位
     * 位仓状态：0-空闲 1-有设备 2-报修 3-开启成功 4-设备摆放位置不正确
     */
    @GET("deviceController/returnOne")
    fun openFreightSpace(@Query("sno") sno: String): Observable<OpenFreightSpaceResponse>

    /**
     * 优惠卷列表  0-未使用 1-已使用 2-已过期 3-全部（默认）
     */
    @GET("personController/myCoupon")
    fun getCoupon(@Query("status") status: Int): Observable<CouponResponse>

    /**
     * 获取消息记录
     */
    @GET("personController/getMessage")
    fun getMessage(@Query("page") page: Int): Observable<MessageResponse>

    /**
     * 设为已读
     */
    @GET("personController/readMessage")
    fun readMsg(@Query("id") id: String): Observable<BaseResponse>

    /**
     * 删除消息  id	int	必填	消息id
    all	int	选填	默认为0， 1：清空

     */
    @GET("personController/delMessage")
    fun delMas(@Query("id") id: String, @Query("all") all: Int): Observable<BaseResponse>

    /**
     * 修改个人信息
     */
    @POST("personController/uptProfile")
    @FormUrlEncoded
    fun modUserInfo(@FieldMap map: Map<String, String>): Observable<BaseResponse>

    /**
     * 更换手机号
     */
    @POST("userController/bindPhone")
    @FormUrlEncoded
    fun modPhone(@Field("areaCode") areaCode: String,
                 @Field("phone") phone: String,
                 @Field("code") code: String,
                 @Field("userId") userId: String): Observable<BaseResponse>

    /**
     * 获取用户的详细信息
     */
    @GET("personController/getProfile")
    fun getUserDetail(): Observable<UserDetailResponse>

    /**
     * 获取附近网店
     */
    @GET("deviceController/getHomeList")
    fun getNearTheSite(@Query("lat") lat: String,
                       @Query("lng") lng: String,
                       @Query("distance") distance: String): Observable<NearTheSitesResponse>

    /**
     * 报失
     */
    @POST("deviceController/lostDevice")
    @FormUrlEncoded
    fun lostDevice(@Field("orderno") orderno: String): Observable<BaseResponse>

    /**
     * 报损
     */
    @POST("deviceController/errorDevice")
    @FormUrlEncoded
    fun breakageDevice(@Field("sno") sno: String,
                       @Field("desc") desc: String,
                       @Field("orderno") orderno: String,
                       @Field("image") image: String
    ): Observable<BaseResponse>

    /**
     * 获取附近网点详情
     */
    @GET("deviceController/getHomeDetail")
    fun getNearTheSiteDetail(@Query("id") id: String): Observable<NearTheSiteDetailResponse>

    /**
     * 获取未结束的一条订单
     * '借还状态：0-初始化 1-使用中 2-待支付  3-已完成  4-报失 5-报损',
     */
    @GET("deviceController/queryUsingDev")
    fun getUsingOrder(): Observable<UsingOrderResponse>

    /**
     * 添加银行卡
     */
    @POST("walletController/bindBank")
    @FormUrlEncoded
    fun addBankCard(@Field("name") name: String,
                    @Field("bank_name") bankName: String,
                    @Field("bank_no") bankNo: String,
                    @Field("bank_expire") bankExpire: String,
                    @Field("bank_cvv") bankCvv: String,
                    @Field("area") area: String
    ): Observable<BaseResponse>

    /**
     * 编辑银行卡
     */
    @POST("walletController/updateBank")
    @FormUrlEncoded
    fun editBankCard(@Field("id") id: String, @Field("country") country: String)
            : Observable<BaseResponse>

    /**
     * 删除(解绑)银行卡
     */
    @POST("walletController/delBankCard")
    @FormUrlEncoded
    fun delBankCard(@Field("id") id: String): Observable<BaseResponse>

    /**
     * 查询银行卡的信息
     */
    @GET("walletController/queryBankDetail")
    fun getBankCardDetail(@Query("id") id: String): Observable<BankCardDetailResponse>

    /**
     * 充值规则
     */
    @GET("walletController/getSystemCharge")
    fun getTopUpRule(): Observable<TopUpRuleResponse>

    /**
     * 5，	获取我的借还记录列表、
     */
    @GET("personController/queryBorrow")
    fun getBorrowRecord(@Query("page") page: Int): Observable<BorrowRecordResponse>

    /**
     * 獲取使用記錄詳情
     */
    @GET("personController/queryBorrowDetail")
    fun getBorrowRecordDetail(@Query("id") id: Int): Observable<BorrowRecordDetailResponse>

    /**
     * 上傳文件
     */
    @Multipart
    @POST("systemController/uploadImage")
    fun uploadImageFile(@Part file: MultipartBody.Part): Observable<UploadImageResponse>

    /**
     * 餘額支付
     */
    @POST("walletController/pay")
    @FormUrlEncoded
    fun payByWallet(@Field("orderno") orderno: String,
                    @Field("payType") payType: Int = 3,
                    @Field("point") point: Int,
                    @Field("cuponId") cuponId: Int
    ): Observable<PayByWalletResponse>

    /**
     * 支付寶支付
     */
    @POST("walletController/pay")
    @FormUrlEncoded
    fun payByAlipay(@Field("orderno") orderno: String,
                    @Field("payType") payType: Int = 1,
                    @Field("point") point: Int,
                    @Field("cuponId") cuponId: Int
    ): Observable<PayByAlipayResponse>

    /**
     * 微信支付
     */
    @POST("walletController/pay")
    @FormUrlEncoded
    fun payByWechar(@Field("orderno") orderno: String,
                    @Field("payType") payType: Int = 0,
                    @Field("point") point: Int,
                    @Field("cuponId") cuponId: Int
    ): Observable<PayByWeChatResponse>

    /**
     * 信用卡(支付)
     */
    @POST("walletController/pay")
    @FormUrlEncoded
    fun payBybankCard(@Field("orderno") orderno: String,
                      @Field("payType") payType: Int = 0,
                      @Field("point") point: Int,
                      @Field("cuponId") cuponId: Int,
                      @Field("backToken") backToken: String,
                      @Field("currency") currency: String
    ): Observable<PayByBankCard>

    /**
     * 获取信用积分和虚拟货币
     */
    @GET("trustPointController/getUserPointAndTrustPoint")
    fun getCreditsAndCurrency(): Observable<CreditAndCurrencyResponse>

    /**
     * 获取信用积分列表记录
     */
    @GET("trustPointController/getUserTrustPointRecord")
    fun getCreditRecordList(@Query("page") page: Int): Observable<CreditRecordListResponse>

    /**
     * 获取虚拟货币列表记录
     */
    @GET("trustPointController/getUserPointRecord")
    fun getCurrencyRecordList(@Query("page") page: Int): Observable<CurrencyRecordListResponse>

    /**
     * 获取信用积分兑换商品列表
     */
    @GET("trustPointController/getproductCreditList")
    fun getCreditsCommodity(@Query("page") page: Int): Observable<CreditCommodityResponse>

    /**
     * 获取信用积分兑换商品详情
     */
    @GET("trustPointController/getproductCreditDetail")
    fun getCreditsCommodityDetail(@Query("productId") productId: String): Observable<CreditCommodityDetailResponse>


    /**
     * 获取收货地址列表
     */
    @GET("trustPointController/getUserAddress")
    fun getShippingAddressList(): Observable<ShippingAddressListResponse>

    /**
     * 新增收货地址
     */
    @POST("trustPointController/addUserAddress")
    @FormUrlEncoded
    fun addShippingAddress(
            @Field("linkman") linkman: String,
            @Field("phone") phone: String,
            @Field("province") province: String,
            @Field("city") city: String,
            @Field("area") area: String,
            @Field("address") address: String,
            @Field("postCode") postCode: String
    ): Observable<BaseResponse>

    /**
     * 刪除收貨地址
     */
    @GET("trustPointController/deleteUserAddress")
    fun delShippingAddress(@Query("id") id: String): Observable<BaseResponse>

    /**
     * 编辑收货地址
     */
    @POST("trustPointController/updateUserAddress")
    @FormUrlEncoded
    fun editShippingAddress(
            @Field("id") id: String,
            @Field("linkman") linkman: String,
            @Field("phone") phone: String,
            @Field("province") province: String,
            @Field("city") city: String,
            @Field("area") area: String,
            @Field("address") address: String,
            @Field("postCode") postCode: String
    ): Observable<BaseResponse>

    /**
     * 设为默认的收货地址
     */
    @GET("trustPointController/udateDefaultAddress")
    fun setDefaultShippingAddress(@Query("id") id: String,
                                  @Query("isDefault") isDefault: Int): Observable<BaseResponse>


    /**
     * 購買商品
     */
    @POST("trustPointController/pointConvertOrder")
    @FormUrlEncoded
    fun payCommodity(
            @Field("consignee") consignee: String,//收貨人姓名
            @Field("phone") phone: String,//電話號碼
            @Field("province") province: String,//省
            @Field("city") city: String,//市
            @Field("area") area: String,//區
            @Field("address") address: String,//詳細地址
            @Field("time") time: String,
            @Field("number") number: Int,
            @Field("productId") productId: String,
            @Field("type") type: Int,
            @Field("payWay") payWay: Int
    ): Observable<BorrowOneResponse>

    /**
     * 確認收貨
     */
    @POST("trustPointController/sureUserOrder")
    @FormUrlEncoded
    fun confirmReceipt(@Field("orderNo") orderNo: String): Observable<BaseResponse>

    /**
     * 獲取商品訂單
     */
    @POST("trustPointController/getUserOrder")
    @FormUrlEncoded
    fun getCommodityOrder(@Field("orderType") orderType: Int, @Field("type") type: Int): Observable<CommodityOrderListResponse>


    /**
     * 獲取相關的頁面
     * key:about—关于我们
    key: agreement—用户协议
    key: help—帮助中心
    key: usage—使用说明
    key: manual—使用手册
    key: rechargeandrefun—充值与退款
    key: termsandconditio-条款与细则
    key: privacypolicy-隐私政策


     */
    @GET("systemController/getAppContent")
    fun getAppHtml(@Query("key") key: String):Observable<HtmlContentResponse>
}