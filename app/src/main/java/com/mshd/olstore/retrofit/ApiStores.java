package com.mshd.olstore.retrofit;


import com.mshd.olstore.base.BaseResp;
import com.mshd.olstore.mvp.model.entity.RespBuildDetails;
import com.mshd.olstore.mvp.model.entity.RespGetToken;
import com.mshd.olstore.mvp.model.entity.RespLogin;
import com.mshd.olstore.mvp.model.entity.RespMain;
import com.mshd.olstore.mvp.model.entity.RespOrder;
import com.mshd.olstore.mvp.model.entity.RespPriceReduction;
import com.mshd.olstore.mvp.model.entity.RespStoreClerk;
import com.mshd.olstore.mvp.model.entity.RespStoreGoods;
import com.mshd.olstore.mvp.model.entity.RespStoreSetting;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiStores {

    /*
     *获取图片文件
     */
    @FormUrlEncoded
    @POST("")
    Observable<ResponseBody> getImg(@Url String mulr, @Field("mobile") String moble);


    /**
     * 获取手机号验证码
     */
    @GET("postpaid/sms/acquirecode")
    Observable<BaseResp> acquirecode(@Query("mobile") String mobile);


    /**
     * 获取短信验证码-前端(根据图片验证码获取短信验证码)
     */
    @POST("postpaid/sms/getPhoneCode")
    Observable<BaseResp> getPhoneCode(@Body RequestBody requestBody);


    /**
     * 多端登录
     */
    @POST("storeLogin/multipointLogin")
    Observable<RespLogin> multipointLogin(@Body RequestBody requestBody);

    /**
     * 获取首页总营业额与总客户
     */
    @POST("store/getStoreList")
    Observable<RespMain> getStoreList(@Body RequestBody requestBody);

    /**
     * 按日查询账单
     */
    @FormUrlEncoded
    @POST("storeTurnover/getOneDayTurnover")
    Observable<RespOrder> getOneDayTurnover(@FieldMap Map<String, String> map);


    /**
     * 按月查询账单
     */
    @FormUrlEncoded
    @POST("storeTurnover/getOneMouthTurnover")
    Observable<RespOrder> getOneMouthTurnover(@FieldMap Map<String, String> map);


    /**
     * 绑定银行卡
     */
    @FormUrlEncoded
    @POST("store/bindingBankCard")
    Observable<RespOrder> bindingBankCard(@FieldMap Map<String, String> map);

    /**
     * 绑定微信
     */
    @FormUrlEncoded
    @POST("store/bindingWX")
    Observable<RespOrder> bindingWX(@FieldMap Map<String, String> map);


    /**
     * 更换结算方式
     */
    @FormUrlEncoded
    @POST("store/changeBinding")
    Observable<RespBuildDetails> changeBinding(@FieldMap Map<String, String> map);

    /**
     * 收入页面绑定信息
     */
    @POST("store/buildDetail")
    Observable<RespBuildDetails> buildDetail(@Body RequestBody requestBody);

    /**
     * 店员列表
     */
    @POST("storeClerk/selectStoreClerk")
    Observable<RespStoreClerk> selectStoreClerk(@Body RequestBody requestBody);


    /**
     * 删除店员
     */
    @FormUrlEncoded
    @POST("storeClerk/deleteclerk")
    Observable<BaseResp> deleteclerk(@FieldMap Map<String, String> map);


    /**
     * 修改店员
     */
    @POST("storeClerk/updateClerk")
    Observable<BaseResp> updateClerk(@Body RequestBody requestBody);


    /**
     * 新增店员（接受邀请）
     */
    @POST("storeClerk/addClerk")
    Observable<BaseResp> addClerk(@Body RequestBody requestBody);


    /**
     * 不同状态下的商品
     */
    @POST("storeGoods/getStoreGoodsByStatus")
    Observable<RespStoreGoods> getStoreGoodsByStatus(@Body RequestBody requestBody);


    /**
     * 增加商铺商品（申请上架）
     */
    @POST("storeGoods/add")
    Observable<BaseResp> storeGoodsAdd(@Body RequestBody requestBody);

    /**
     * 已上架商品->下架
     */
    @FormUrlEncoded
    @POST("storeGoods/lowerShelf")
    Observable<BaseResp> lowerShelf(@FieldMap Map<String, String> map);

    /**
     * 已下架商品->上架
     */
    @FormUrlEncoded
    @POST("storeGoods/upperShelf")
    Observable<BaseResp> upperShelf(@FieldMap Map<String, String> map);

    /**
     * 删除商铺商品
     */
    @FormUrlEncoded
    @POST("storeGoods/delete")
    Observable<BaseResp> storeGoodsDelete(@FieldMap Map<String, String> map);

    /**
     * 商铺商品入库
     */
    @POST("storeGoods/warehousing")
    Observable<BaseResp> storeGoodsRuKu(@Body RequestBody requestBody);


    /**
     * 全部商品（响应类型状态+数量 ，用于刷新tab数量）
     */
    @POST("storeGoods/allStoreGoods")
    Observable<BaseResp> allStoreGoods(@Body RequestBody requestBody);


    /**
     * 获取商铺信息
     */
    @POST("store/getStoreDetail")
    Observable<RespStoreSetting> getStoreDetail(@Body RequestBody requestBody);

    /**
     * 修改商铺名称
     */
    @POST("store/updateStoreName")
    Observable<BaseResp> updateStoreName(@Body RequestBody requestBody);


    /**
     * 减价规则
     */
    @POST("storeGoods/priceReduction")
    Observable<RespPriceReduction> priceReduction(@Body RequestBody requestBody);

    /**
     * 申请上小学付
     */
    @POST("userPower/applySXXPay")
    Observable<BaseResp> applySXXPay(@Body RequestBody requestBody);


    /**
     * 点击气泡消除
     */
    @FormUrlEncoded
    @POST("oad/clickBubble")
    Observable<BaseResp> clickBubble(@FieldMap Map<String, String> map);


    /**
     * 签到消消乐
     */
    @POST("eliminate/getSignInfo")
    Observable<BaseResp> getSignInfo(@Body RequestBody requestBody);


    /**
     * 添加每日答题
     */
    @POST("DailyAnswer/add")
    Observable<BaseResp> dailyAnswer(@Body RequestBody requestBody);

    /**
     * 订单退款
     */
    @GET("order/refund")
    Observable<BaseResp> refund(@Query("orderNo") String orderNo);

    /**
     * 阿里云OSS获取签名信息
     */
    @GET("aliyunOss/getToken")
    Observable<RespGetToken> getToken(@Query("type") String type);


}
