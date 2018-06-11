package com.purchase.sls.data.remote;


import com.purchase.sls.data.RemoteDataException;
import com.purchase.sls.data.RemoteDataWrapper;
import com.purchase.sls.data.entity.AccountDetailInfo;
import com.purchase.sls.data.entity.AccountListInfo;
import com.purchase.sls.data.entity.ActivityInfo;
import com.purchase.sls.data.entity.ActivityOrderDetailInfo;
import com.purchase.sls.data.entity.ActivityOrderListResponse;
import com.purchase.sls.data.entity.AddressInfo;
import com.purchase.sls.data.entity.AliPaySignResponse;
import com.purchase.sls.data.entity.AllEvaluationInfo;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.BrowseInfo;
import com.purchase.sls.data.entity.ChangeAppInfo;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.entity.CouponListInfo;
import com.purchase.sls.data.entity.EnergyInfo;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.IntercourseRecordInfo;
import com.purchase.sls.data.entity.LikeStoreResponse;
import com.purchase.sls.data.entity.MapMarkerInfo;
import com.purchase.sls.data.entity.MessageListInfo;
import com.purchase.sls.data.entity.NearbyInfoResponse;
import com.purchase.sls.data.entity.OrderDetailInfo;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.data.entity.ScreeningListResponse;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.entity.ToBeEvaluationInfo;
import com.purchase.sls.data.entity.UserpowerInfo;
import com.purchase.sls.data.entity.WXPaySignResponse;
import com.purchase.sls.data.request.AccountDetailRequest;
import com.purchase.sls.data.request.AccountListRequest;
import com.purchase.sls.data.request.ActivityIdRequest;
import com.purchase.sls.data.request.ActivityOrderListRequest;
import com.purchase.sls.data.request.AddAddressRequest;
import com.purchase.sls.data.request.AddRemoveCollectionRequest;
import com.purchase.sls.data.request.ChangeUserInfoRequest;
import com.purchase.sls.data.request.ChangepwdRequest;
import com.purchase.sls.data.request.CheckCodeRequest;
import com.purchase.sls.data.request.BannerHotRequest;
import com.purchase.sls.data.request.CheckNewCodeRequest;
import com.purchase.sls.data.request.CollectionListRequest;
import com.purchase.sls.data.request.CouponListRequest;
import com.purchase.sls.data.request.DetectionVersionRequest;
import com.purchase.sls.data.request.EnergyInfoRequest;
import com.purchase.sls.data.request.GeneratingOrderRequest;
import com.purchase.sls.data.request.IdRequest;
import com.purchase.sls.data.request.IntercourseRecordRequest;
import com.purchase.sls.data.request.LikeStoreRequest;
import com.purchase.sls.data.request.LoginRequest;
import com.purchase.sls.data.request.MapMarkerRequest;
import com.purchase.sls.data.request.MessageListRequest;
import com.purchase.sls.data.request.NearbyInfoRequest;
import com.purchase.sls.data.request.OrderCodeRequest;
import com.purchase.sls.data.request.OrderDetailRequest;
import com.purchase.sls.data.request.PageRequest;
import com.purchase.sls.data.request.PhoneLoginRequest;
import com.purchase.sls.data.request.RegisterPasswordRequest;
import com.purchase.sls.data.request.RemoveBrowseRequest;
import com.purchase.sls.data.request.ScreeningListRequest;
import com.purchase.sls.data.request.SendCodeRequest;
import com.purchase.sls.data.request.SendNewVCodeRequest;
import com.purchase.sls.data.request.ShopDetailsRequest;
import com.purchase.sls.data.request.StoreIdPageRequest;
import com.purchase.sls.data.request.SubmitEvaluateRequest;
import com.purchase.sls.data.request.SubmitSpikeRequest;
import com.purchase.sls.data.request.TokenRequest;
import com.purchase.sls.data.request.TypeRequest;
import com.purchase.sls.data.request.UploadXyRequest;
import com.purchase.sls.data.request.UserpowerRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //密码登录
    @POST("home/login/dologin")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> accountLogin(@Body LoginRequest loginRequest);

    //发送验证码
    @POST("home/login/sendcode")
    Flowable<RemoteDataWrapper<Ignore>> sendCode(@Body SendCodeRequest sendCodeRequest);

    //短信登录
    @POST("home/login/bycode")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> phoneLogin(@Body PhoneLoginRequest phoneLoginRequest);

    //注册/修改密码
    @POST("home/login/register")
    Flowable<RemoteDataWrapper<Ignore>> registerPassword(@Body RegisterPasswordRequest registerPasswordRequest);

    //修改密码
    @POST("home/login/changepwd")
    Flowable<RemoteDataWrapper<Ignore>> changepwd(@Body ChangepwdRequest changepwdRequest);

    //验证验证码
    @POST("home/login/checkCode")
    Flowable<RemoteDataWrapper<Ignore>> checkCode(@Body CheckCodeRequest checkCodeRequest);

    //获取首页banner数据和热门数据
    @POST("home/index/address")
    Flowable<RemoteDataWrapper<BannerHotResponse>> getBannerHotInfo(@Body BannerHotRequest bannerHotRequest);

    //获取首页猜你喜欢
    @POST("home/index/getLikeStore")
    Flowable<RemoteDataWrapper<LikeStoreResponse>> getLikeStoreInfo(@Body LikeStoreRequest likeStoreRequest);

    //获取附近地图中的数据
    @POST("home/nearby")
    Flowable<RemoteDataWrapper<List<NearbyInfoResponse>>> getNearbyInfo(@Body NearbyInfoRequest nearbyInfoRequest);

    //地图marker的list
    @POST("home/getinfostore")
    Flowable<RemoteDataWrapper<List<MapMarkerInfo>>> getMapMarkerInfo(@Body MapMarkerRequest mapMarkerRequest);

    //获取选择首页几个图片跳转页面的额数据
    @POST("home/index/appGetCate")
    Flowable<RemoteDataWrapper<ScreeningListResponse>> getScreeningListInfo(@Body ScreeningListRequest request);

    //获取店铺详情
    @POST("home/index/getstoreinfo")
    Flowable<RemoteDataWrapper<ShopDetailsInfo>> getShopDetailsInfo(@Body ShopDetailsRequest shopDetailsRequest);

    //获取收藏列表
    @POST("home/storefavo/getlist")
    Flowable<RemoteDataWrapper<CollectionListResponse>> getCollectionListInfo(@Body CollectionListRequest collectionListRequest);

    //删除和增加收藏
    @POST("home/storefavo/favorite")
    Flowable<RemoteDataWrapper<Ignore>> addRemoveCollection(@Body AddRemoveCollectionRequest addRemoveCollectionRequest);

    //支付订单页面获取能量
    @POST("home/login/getuserpower")
    Flowable<RemoteDataWrapper<UserpowerInfo>> getUserpowerInfo(@Body UserpowerRequest userpowerRequest);

    //生成订单(能量支付)
    @POST("pay/create")
    Flowable<RemoteDataWrapper<GeneratingOrderInfo>> getGeneratingOrderInfo(@Body GeneratingOrderRequest generatingOrderRequest);

    //生成订单(支付宝支付)
    @POST("pay/create")
    Flowable<RemoteDataWrapper<AliPaySignResponse>> getAliPaySignResponse(@Body GeneratingOrderRequest generatingOrderRequest);

    //生成订单(微信支付)
    @POST("pay/create")
    Flowable<RemoteDataWrapper<WXPaySignResponse>> getWXPaySignResponse(@Body GeneratingOrderRequest generatingOrderRequest);

    //账单列表
    @POST("home/bill/userbill")
    Flowable<RemoteDataWrapper<AccountListInfo>> getAccountListInfo(@Body AccountListRequest accountListRequest);

    //账单详情
    @POST("home/bill/billXiangqing")
    Flowable<RemoteDataWrapper<AccountDetailInfo>> getAccountDetail(@Body AccountDetailRequest accountDetailRequest);

    //来往记录
    @POST("home/bill/suBill")
    Flowable<RemoteDataWrapper<IntercourseRecordInfo>> getIntercourseRecordInfo(@Body IntercourseRecordRequest intercourseRecordRequest);

    //获取优惠券列表
    @POST("home/quan/getlist")
    Flowable<RemoteDataWrapper<CouponListInfo>> getCouponListInfo(@Body CouponListRequest couponListRequest);

    //获取能量
    @POST("home/userLogController/userLogHistory")
    Flowable<RemoteDataWrapper<EnergyInfo>> getEnergyInfo(@Body EnergyInfoRequest energyInfoRequest);

    //获取访问记录
    @POST("home/index/getUserLog")
    Flowable<RemoteDataWrapper<BrowseInfo>> getBrowseInfo(@Body PageRequest request);

    //删除浏览记录
    @POST("home/index/deleteUserLog")
    Flowable<RemoteDataWrapper<Ignore>> removeBrowse(@Body RemoveBrowseRequest removeBrowseRequest);

    //修改个人信息
    @POST("home/login/changeuserinfo")
    Flowable<RemoteDataWrapper<Ignore>> changeUserInfo(@Body ChangeUserInfoRequest changeUserInfoRequest);

    //发送新手机的验证码
    @POST("home/login/smsNewTel")
    Flowable<RemoteDataWrapper<Ignore>> sendNewVcode(@Body SendNewVCodeRequest sendNewVCodeRequest);

    //修改手机号
    @POST("home/login/changeNewTel")
    Flowable<RemoteDataWrapper<Ignore>> checkNewCode(@Body CheckNewCodeRequest checkNewCodeRequest);
    //上传头像

    /**
     * 修改头像
     */
    @Multipart
    @POST("home/login/changeAvatar")
    Flowable<RemoteDataWrapper<String>> changeAvatar(@PartMap Map<String, RequestBody> multipartParams);

    //版本检测
    @POST("home/changeApp")
    Flowable<RemoteDataWrapper<ChangeAppInfo>> changeApp(@Body DetectionVersionRequest detectionVersionRequest);

    //获取评价列表
    @POST("home/business/getEvaluate")
    Flowable<RemoteDataWrapper<AllEvaluationInfo>> getAllEvaluation(@Body StoreIdPageRequest storeIdPageRequest);

    //待评价列表
    @POST("home/consume/waitevaluate")
    Flowable<RemoteDataWrapper<ToBeEvaluationInfo>> getToBeEvaluation(@Body PageRequest pageRequest);

    //提交评价
    @POST("home/index/evaluate")
    Flowable<RemoteDataWrapper<String>> submitEvalute(@Body SubmitEvaluateRequest submitEvaluateRequest);

    //上传图片(阿里云图片上传)
    @Multipart
    @POST("home/index/uploadFile")
    Flowable<RemoteDataWrapper<String>> uploadFile(@PartMap Map<String, RequestBody> multipartParams);

    //订单详情
    @POST("home/quan/present")
    Flowable<RemoteDataWrapper<OrderDetailInfo>> getOrderDetailInfo(@Body OrderDetailRequest orderDetailRequest);

    //消息中心
    @POST("home/user/Message")
    Flowable<RemoteDataWrapper<MessageListInfo>> getMessageListInfo(@Body MessageListRequest messageListRequest);

    //地址列表
    @POST("home/address/addresslist")
    Flowable<RemoteDataWrapper<List<AddressInfo>>> getAddressList(@Body TokenRequest tokenRequest);

    //设为默认
    @POST("home/address/setDefault")
    Flowable<RemoteDataWrapper<Ignore>> setDefault(@Body IdRequest idRequest);

    //删除地址
    @POST("home/address/delete")
    Flowable<RemoteDataWrapper<Ignore>> deleteAddress(@Body IdRequest idRequest);

    //修改添加地址
    @POST("home/address/post")
    Flowable<RemoteDataWrapper<String>> addAddress(@Body AddAddressRequest addAddressRequest);

    //上传坐标
    @POST("home/user/userRoute")
    Flowable<RemoteDataWrapper<Ignore>> uploadXy(@Body UploadXyRequest uploadXyRequest);

    //获取活动
    @POST("home/activityList/list")
    Flowable<RemoteDataWrapper<List<ActivityInfo>>> getActivityInfos(@Body TypeRequest typeRequest);

    //签到
    @POST("home/signin/sign")
    Flowable<RemoteDataWrapper<String>> signIn(@Body TokenRequest tokenRequest);

    //秒杀和兑换下单
    @POST("home/secKill")
    Flowable<RemoteDataWrapper<ActivityOrderDetailInfo>> submitSpike(@Body SubmitSpikeRequest submitSpikeRequest);

    //抽奖下单
    @POST("home/drawOrder")
    Flowable<RemoteDataWrapper<ActivityOrderDetailInfo>> submitLottery(@Body SubmitSpikeRequest submitSpikeRequest);

    //活动的订单列表
    @POST("home/orderList")
    Flowable<RemoteDataWrapper<ActivityOrderListResponse>> getActivityOrderList(@Body ActivityOrderListRequest activityOrderListRequest);

    //活动订单详情
    @POST("home/actorder/orderDetail")
    Flowable<RemoteDataWrapper<ActivityOrderDetailInfo>> getActivityOrderDetail(@Body IdRequest idRequest);

    //活动详情接口
    @POST("home/activityList/detail")
    Flowable<RemoteDataWrapper<ActivityInfo>> getActivityInfo(@Body ActivityIdRequest activityIdRequest);

    //删除订单
    @POST("home/actorder/deleteOrder")
    Flowable<RemoteDataWrapper<Ignore>> deleteActivityOrder(@Body IdRequest idRequest);

    //确认收货
    @POST("home/actorder/confirmOrder")
    Flowable<RemoteDataWrapper<Ignore>> confirmActivityOrder(@Body OrderCodeRequest orderCodeRequest);

    //分享
    @POST("home/share")
    Flowable<RemoteDataWrapper<String>> share(@Body TokenRequest tokenRequest);

}
