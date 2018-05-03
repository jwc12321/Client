package com.purchase.sls.data.remote;


import com.purchase.sls.data.RemoteDataWrapper;
import com.purchase.sls.data.entity.AccountDetailInfo;
import com.purchase.sls.data.entity.AccountListInfo;
import com.purchase.sls.data.entity.BannerHotResponse;
import com.purchase.sls.data.entity.CollectionListResponse;
import com.purchase.sls.data.entity.CouponListInfo;
import com.purchase.sls.data.entity.GeneratingOrderInfo;
import com.purchase.sls.data.entity.Ignore;
import com.purchase.sls.data.entity.IntercourseRecordInfo;
import com.purchase.sls.data.entity.LikeStoreResponse;
import com.purchase.sls.data.entity.NearbyInfoResponse;
import com.purchase.sls.data.entity.PersionInfoResponse;
import com.purchase.sls.data.entity.ScreeningListResponse;
import com.purchase.sls.data.entity.ShopDetailsInfo;
import com.purchase.sls.data.entity.UserpowerInfo;
import com.purchase.sls.data.request.AccountDetailRequest;
import com.purchase.sls.data.request.AccountListRequest;
import com.purchase.sls.data.request.AddRemoveCollectionRequest;
import com.purchase.sls.data.request.CheckCodeRequest;
import com.purchase.sls.data.request.BannerHotRequest;
import com.purchase.sls.data.request.CollectionListRequest;
import com.purchase.sls.data.request.CouponListRequest;
import com.purchase.sls.data.request.GeneratingOrderRequest;
import com.purchase.sls.data.request.IntercourseRecordRequest;
import com.purchase.sls.data.request.LikeStoreRequest;
import com.purchase.sls.data.request.LoginRequest;
import com.purchase.sls.data.request.NearbyInfoRequest;
import com.purchase.sls.data.request.PhoneLoginRequest;
import com.purchase.sls.data.request.RegisterPasswordRequest;
import com.purchase.sls.data.request.ScreeningListRequest;
import com.purchase.sls.data.request.SendCodeRequest;
import com.purchase.sls.data.request.ShopDetailsRequest;
import com.purchase.sls.data.request.UserpowerRequest;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

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

    //生成订单
    @POST("pay/create")
    Flowable<RemoteDataWrapper<GeneratingOrderInfo>> getGeneratingOrderInfo(@Body GeneratingOrderRequest generatingOrderRequest);

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

}
