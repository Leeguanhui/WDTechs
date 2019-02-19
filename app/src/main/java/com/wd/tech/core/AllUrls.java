package com.wd.tech.core;

import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.NewsBannder;
import com.wd.tech.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者：夏洪武
 * 时间：2019/2/18.
 * 邮箱：
 * 说明：
 */
public interface AllUrls {
    //查询咨询首页Bander图
    @GET("information/v1/bannerShow")
    Observable<Result<List<NewsBannder>>> Bannder(
    );

    /**
     * 社区列表
     */
    @GET("community/v1/findCommunityList")
    Observable<Result<List<CommunityListBean>>> communityList(/*@Header("userId") int userId, @Header("sessionId") String sessionId,*/
                                                              @Query("page") int page, @Query("count") int count);

    //用户登录
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<LoginUserInfoBean>> login(
            @Field("phone") String phone,
            @Field("pwd") String pwd);
    //查询咨询首页Bander图
    @GET("information/v1/infoRecommendList")
    Observable<Result<List<InfoRecommecndListBean>>> InFormation(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("plateId")int plateId,
            @Query("page")int page,
            @Query("count")int count
    );
}
