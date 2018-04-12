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
    @GET("userController/phoneCode")
    fun getVerCode(@Query("areaCode") areaCode: String,
                   @Query("phone") phone: String,
                   @Query("used_to") userTo: String): Observable<BaseResponse>

    /**
     * 登錄  1：账号，密码  2：uid,token
    3：第三方  4:手机号，证码

     */
    @POST("userController/login")
    @FormUrlEncoded
    fun login(@Field("areaCode") areaCode: String,
              @Field("login_type") type: String,
              @Field("open_type") openType: String?,
              @Field("account") account: String,
              @Field("password") password: String): Observable<UserResponse>

    /**
     * 注册
     */
    @POST("userController/userRegister")
    @FormUrlEncoded
    fun register(@Field("areaCode") areaCode: String,
                 @Field("phone") phone: String,
                 @Field("code") code: String,
                 @Field("surname") surName: String,
                 @Field("realName") realname: String,
            //  @Field("password") pwd: String,
                 @Field("email") email: String,
                 @Field("father_id") fatherId: String): Observable<BaseResponse>

    /**
     * 獲取邀請用戶列表
     */
    @GET("personController/myInvites")
    fun getInvite(@Query("page") page: Int): Observable<InvitesResponse>


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
    @GET("personController/queryBank")
    fun getBankCard(): Observable<BankCardResponse>

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
                    @Field("bank_cvv") bankCvv: String
    ): Observable<BaseResponse>

    /**
     * 删除(解绑)银行卡
     */
    @POST("walletController/delBankCard")
    @FormUrlEncoded
    fun delBankCard(@Field("id") id: String): Observable<BaseResponse>

    /**
     * 查询银行卡的信息
     */
    @GET("personController/queryBankDetail")
    fun getBankCardDetail(): Observable<BankCardDetailResponse>

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
     * 上傳文件
     */
    @Multipart
    @POST("systemController/uploadImage")
    fun uploadImageFile(@Part file: MultipartBody.Part): Observable<UploadImageResponse>
}