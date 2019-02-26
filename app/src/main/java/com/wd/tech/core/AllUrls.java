package com.wd.tech.core;

import com.wd.tech.activity.view.Type;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.FindCollectBean;
import com.wd.tech.bean.FindFriendNoticePageList;
import com.wd.tech.bean.FindGroupInfo;
import com.wd.tech.bean.FindGroupNoticePageList;
import com.wd.tech.bean.FindGroupsByUserId;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.InitFriendlist;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.NewsBannder;
import com.wd.tech.bean.NewsDetailsBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.TypeBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * 作者：夏洪武
 * 时间：2019/2/18.
 * 邮箱：
 * 说明：
 */
public interface AllUrls {
    /**
     * 查询咨询首页Bander图
     */
    @GET("information/v1/bannerShow")
    Observable<Result<List<NewsBannder>>> Bannder(
    );

    /**
     * 社区列表
     */
    @GET("community/v1/findCommunityList")
    Observable<Result<List<CommunityListBean>>> communityList(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                              @Query("page") int page, @Query("count") int count);

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result<LoginUserInfoBean>> login(
            @Field("phone") String phone,
            @Field("pwd") String pwd);

    /**
     * 查询咨询首页Bander图
     */
    @GET("information/v1/infoRecommendList")
    Observable<Result<List<InfoRecommecndListBean>>> InFormation(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("plateId") int plateId,
            @Query("page") int page,
            @Query("count") int count);

    /**
     * 用户注册
     */
    @FormUrlEncoded
    @POST("user/v1/register")
    Observable<Result> register(
            @Field("phone") String phone,
            @Field("nickName") String nickName,
            @Field("pwd") String pwd);

    /**
     * 根据用户ID查询用户信息
     */
    @GET("user/verify/v1/getUserInfoByUserId")
    Observable<Result<ByIdUserInfoBean>> getUserInfoByUserId(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId);

    /**
     * 用户收藏列表
     */
    @GET("user/verify/v1/findAllInfoCollection")
    Observable<Result<List<FindCollectBean>>> findAllInfoCollection(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("page") int page, @Query("count") int count);

    /**
     * 咨询详情
     */
    @GET("information/v1/findInformationDetails")
    Observable<Result<NewsDetailsBean>> NewsDetails(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("id") int id
    );

    /**
     * 查找好友
     */
    @GET("user/verify/v1/findUserByPhone")
    Observable<Result<ByIdUserInfoBean>> addFriend(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("phone") String phone);

    /**
     * 查找群
     */
    @GET("group/verify/v1/findGroupInfo")
    Observable<Result<FindGroupInfo>> findGroupInfo(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,
                                                    @Query("groupId") int groupId);

    /**
     * 详情评论
     */
    @GET("information/v1/findAllInfoCommentList")
    Observable<Result<NewsDetailsBean>> NewsComments(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("infoId") int infoId,
            @Query("page") int page,
            @Query("count") int count
    );

    //初始化好友分组
    @GET("chat/verify/v1/initFriendList")
    Observable<Result<List<InitFriendlist>>> initFriendList(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId
    );

    /**
     * 修改个性签名
     */
    @PUT("user/verify/v1/modifySignature")
    Observable<Result> modifySignature(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId,
                                       @Query("signature") String signature);

    /**
     * 发布帖子
     */
    @POST("community/verify/v1/releasePost")
    Observable<Result> releasePost(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Body MultipartBody body);

    /**
     * 社区点赞
     */
    @POST("community/verify/v1/addCommunityGreat")
    Observable<Result> addCommunityGreat(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("communityId") int communityId);

    /**
     * 修改用户名称
     */
    @PUT("user/verify/v1/modifyNickName")
    Observable<Result> modifyNickName(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("nickName") String nickName);

    /**
     * 创建群
     */
    @FormUrlEncoded
    @POST("group/verify/v1/createGroup")
    Observable<Result> createGroup(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Field("name") String name,
                                   @Field("description") String description
    );

    /**
     * 查询所有版块
     */
    @GET("information/v1/findAllInfoPlate")
    Observable<Result<List<TypeBean>>> Type(
    );

    /**
     * 添加好友
     */
    @FormUrlEncoded
    @POST("chat/verify/v1/addFriend")
    Observable<Result> addFriendUser(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Field("friendUid") int friendUid,
                                     @Field("remark") String remark);

    /**
     * 申请加群
     */
    @FormUrlEncoded
    @POST("group/verify/v1/applyAddGroup")
    Observable<Result> applyAddGroup(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Field("groupId") int groupId,
                                     @Field("remark") String remark);

    /**
     * 查询我创建的群组
     */
    @GET("group/verify/v1/findUserJoinedGroup")
    Observable<Result<List<FindGroupsByUserId>>> findGroupsByUserId(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId
    );

    /**
     * https://172.17.8.100/techApi/chat/verify/v1/findFriendNoticePageList
     * 查询新朋友的界面
     */
    @GET("chat/verify/v1/findFriendNoticePageList")
    Observable<Result<List<FindFriendNoticePageList>>> findFriendNoticePageList(@Header("userId") int userId,
                                                                                @Header("sessionId") String sessionId,
                                                                                @Query("page") int page, @Query("count") int count);

    /**
     * https://172.17.8.100/techApi/group/verify/v1/findGroupNoticePageList
     * 查询群聊界面findgroupnoticepagelist
     */
    @GET("group/verify/v1/findGroupNoticePageList")
    Observable<Result<List<FindGroupNoticePageList>>> findGroupNoticePageList(@Header("userId") int userId,
                                                                              @Header("sessionId") String sessionId,
                                                                              @Query("page") int page, @Query("count") int count);

    /**
     * https://172.17.8.100/techApi/chat/verify/v1/checkMyFriend
     * .检测是否为我的好友
     */
    @GET("chat/verify/v1/checkMyFriend")
    Observable<Result> checkMyFriend(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Query("friendUid") int friendUid

    );
}
