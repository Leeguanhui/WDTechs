package com.wd.tech.core;

import com.wd.tech.activity.view.Type;
import com.wd.tech.bean.AllCommentBean;
import com.wd.tech.bean.AttUserListBean;
import com.wd.tech.bean.ByIdUserInfoBean;
import com.wd.tech.bean.ByTitleBean;
import com.wd.tech.bean.CommunityListBean;
import com.wd.tech.bean.CommunityUserCommentListBean;
import com.wd.tech.bean.FindCollectBean;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.bean.FindFriendNoticePageList;
import com.wd.tech.bean.FindGroupInfo;
import com.wd.tech.bean.FindGroupNoticePageList;
import com.wd.tech.bean.FindGroupsByCreate;
import com.wd.tech.bean.FindGroupsByUserId;
import com.wd.tech.bean.FindMyPostListBean;
import com.wd.tech.bean.FindUserTaskListBean;
import com.wd.tech.bean.FriendGroup;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.GroupMember;
import com.wd.tech.bean.InfoRecommecndListBean;
import com.wd.tech.bean.InitFriendlist;
import com.wd.tech.bean.LoginUserInfoBean;
import com.wd.tech.bean.NewsBannder;
import com.wd.tech.bean.NewsDetailsBean;
import com.wd.tech.bean.NotifiListBean;
import com.wd.tech.bean.QueryFriendInformationBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.TypeBean;
import com.wd.tech.bean.UserIntegralBean;
import com.wd.tech.bean.UserIntegralBean;
import com.wd.tech.bean.UserIntegralListBean;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Observable<Result<List<AllCommentBean>>> NewsComments(
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
     * 根据标题模糊查询
     */
    @GET("information/v1/findInformationByTitle")
    Observable<Result<List<ByTitleBean>>> findInformationByTitle(@Query("title") String title, @Query("page") int page,
                                                                 @Query("count") int count);

    /**
     * 根据作者名模糊查询
     */
    @GET("information/v1/findInformationBySource")
    Observable<Result<List<ByTitleBean>>> findName(@Query("source") String source, @Query("page") int page,
                                                   @Query("count") int count);

    /**
     * 完善用户信息
     */
    @FormUrlEncoded
    @POST("user/verify/v1/perfectUserInfo")
    Observable<Result> perfectUserInfo(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId,
                                       @Field("nickName") String nickName, @Field("sex") int sex,
                                       @Field("signature") String signature, @Field("birthday") String birthday,
                                       @Field("email") String email);

    /**
     * 用户签到
     */
    @POST("user/verify/v1/userSign")
    Observable<Result> userSign(@Header("userId") int userId,
                                @Header("sessionId") String sessionId);

    /**
     * 查看用户当天签到状态
     */
    @GET("user/verify/v1/findUserSignStatus")
    Observable<Result> findUserSignStatus(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId);

    /**
     * 修改用户头像
     */
    @POST("user/verify/v1/modifyHeadPic")
    Observable<Result> modifyHeadPic(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Body MultipartBody body);

    /**
     * 查询用户当月所有签到的日期
     */
    @GET("user/verify/v1/findUserSignRecording")
    Observable<Result> findUserSignRecording(@Header("userId") int userId,
                                             @Header("sessionId") String sessionId);

    /**
     * 微信登录
     */
    @FormUrlEncoded
    @POST("user/v1/weChatLogin")
    Observable<Result<LoginUserInfoBean>> weChatLogin(@Header("ak") String ak,
                                                      @Field("code") String code);

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
     * 查询我所有加入的群组
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

    /**
     * .社区用户评论列表
     */
    @GET("community/v1/findCommunityUserCommentList")
    Observable<Result<List<CommunityUserCommentListBean>>> findCommunityUserCommentList(@Header("userId") int userId,
                                                                                        @Header("sessionId") String sessionId,
                                                                                        @Query("communityId") int communityId,
                                                                                        @Query("page") int page,
                                                                                        @Query("count") int count);

    /**
     * 查询用户发布的帖子
     */
    @GET("community/verify/v1/findUserPostById")
    Observable<Result<List<CommunityListBean>>> findUserPostById(@Header("userId") int userId,
                                                                 @Header("sessionId") String sessionId,
                                                                 @Query("fromUid") int fromUid,
                                                                 @Query("page") int page,
                                                                 @Query("count") int count);

    /**
     * 做任务
     */
    @FormUrlEncoded
    @POST("user/verify/v1/doTheTask")
    Observable<Result> doTheTask(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                 @Field("taskId") int taskId);

    /**
     * 查看任务
     */
    @GET("user/verify/v1/findUserTaskList")
    Observable<Result<List<FindUserTaskListBean>>> findUserTaskList(@Header("userId") int userId,
                                                                    @Header("sessionId") String sessionId);

    /**
     * 咨询评论
     */
    @POST("information/verify/v1/addInfoComment")
    Observable<Result> AddComments(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("content") String content,
                                   @Query("infoId") String infoId);

    /**
     * 咨询点赞
     */
    @POST("information/verify/v1/addGreatRecord")
    Observable<Result> AddGreat(@Header("userId") int userId,
                                @Header("sessionId") String sessionId,
                                @Query("infoId") int infoId);

    /**
     * 取消点赞
     */
    @DELETE("information/verify/v1/cancelGreat")
    Observable<Result> CancelGreat(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("infoId") int infoId);

    /**
     * 收藏
     */
    @POST("user/verify/v1/addCollection")
    Observable<Result> AddCollection(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Query("infoId") int infoId);

    /**
     * 取消收藏
     */
    @DELETE("user/verify/v1/cancelCollection")
    Observable<Result> cancelCollection(@Header("userId") int userId,
                                        @Header("sessionId") String sessionId,
                                        @Query("infoId") String infoId);

    /**
     * 用户积分
     */
    @GET("user/verify/v1/findUserIntegral")
    Observable<Result<UserIntegralBean>> UserJf(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId
    );

    /**
     * 积分兑换资讯
     */
    @POST("information/verify/v1/infoPayByIntegral")
    Observable<Result> UserExchange(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("infoId") int infoId,
                                    @Query("integralCost") int integralCost
    );

    /**
     * 关注列表
     */
    @GET("user/verify/v1/findFollowUserList")
    Observable<Result<List<AttUserListBean>>> findFollowUserList(@Header("userId") int userId,
                                                                 @Header("sessionId") String sessionId,
                                                                 @Query("page") int page, @Query("count") int count);

    /**
     * 系统通知
     */
    @GET("tool/verify/v1/findSysNoticeList")
    Observable<Result<List<NotifiListBean>>> findSysNoticeList(@Header("userId") int userId,
                                                               @Header("sessionId") String sessionId,
                                                               @Query("page") int page, @Query("count") int count);

    /**
     * 用户积分
     */
    @GET("user/verify/v1/findUserIntegral")
    Observable<Result<UserIntegralBean>> findUserIntegral(@Header("userId") int userId,
                                                          @Header("sessionId") String sessionId);

    /**
     * 用户积分明细列表
     */
    @GET("user/verify/v1/findUserIntegralRecord")
    Observable<Result<List<UserIntegralListBean>>> findUserIntegralRecord(@Header("userId") int userId, @Header("sessionId") String sessionId,
                                                                          @Query("page") int page, @Query("count") int count);

    /**
     * 审核群申请
     */
    @PUT("group/verify/v1/reviewGroupApply")
    Observable<Result> reviewGroupApply(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("noticeId") int noticeId,
            @Query("flag") int flag
    );

    /**
     * 审核好友申请
     */
    @PUT("chat/verify/v1/reviewFriendApply")
    Observable<Result> reviewFriendApply(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Query("noticeId") int noticeId,
            @Query("flag") int flag
    );

    /**
     * 删除好友
     *
     * @param userId
     * @param sessionId
     * @param friendUid
     * @return
     */
    @DELETE("chat/verify/v1/deleteFriendRelation")
    Observable<Result> deleteFriendRelation(@Header("userId") int userId,
                                            @Header("sessionId") String sessionId,
                                            @Query("friendUid") int friendUid);

    /**
     * 转移好友到其他分组
     *
     * @param userId
     * @param sessionId
     * @param newGroupId
     * @param friendUid
     * @return
     */
    @PUT("chat/verify/v1/transferFriendGroup")
    @FormUrlEncoded
    Observable<Result> transferFriendGroup(@Header("userId") int userId,
                                           @Header("sessionId") String sessionId,
                                           @Field("newGroupId") int newGroupId,
                                           @Field("friendUid") int friendUid);

    //判断用户是否已经在群内
    @GET("group/verify/v1/whetherInGroup")
    Observable<Result> whetherInGroup(@Header("userid") int userid,
                                      @Header("sessionid") String sessionid,
                                      @Query("groupId") int groupId);

    //查询我创建的群组
    @GET("group/verify/v1/findGroupsByUserId")
    Observable<Result<List<FindGroupsByCreate>>> findGroupsByCreate(@Header("userid") int userid,
                                                                    @Header("sessionid") String sessionid);

    /**
     * 退群
     */
    @DELETE("group/verify/v1/retreat")
    Observable<Result> retreat(
            @Header("userid") int userid,
            @Header("sessionid") String sessionid,
            @Query("groupId") int groupId);

    /**
     * 连续签到天数
     */
    @GET("user/verify/v1/findContinuousSignDays")
    Observable<Result> findContinuousSignDays(
            @Header("userid") int userid,
            @Header("sessionid") String sessionid
    );

    /**
     * 社区评论
     */
    @POST("community/verify/v1/addCommunityComment")
    Observable<Result> addCommunityComment(@Header("userid") int userid,
                                           @Header("sessionid") String sessionid,
                                           @Query("communityId") int communityId,
                                           @Query("content") String content);

    /**
     * 用户购买VIP
     */
    @POST("tool/verify/v1/buyVip")
    Observable<Result> PAYVIP(@Header("userId") int userId,
                              @Header("sessionId") String sessionId,
                              @Query("commodityId") int commodityId,
                              @Query("sign") String sign
    );

    /**
     * 支付
     */
    @POST("tool/verify/v1/pay")
    Observable<Result> PAY(@Header("userId") int userId,
                           @Header("sessionId") String sessionId,
                           @Query("orderId") String orderId, @Query("payType") int payType);

    /**
     * 我的帖子
     */
    @GET("community/verify/v1/findMyPostById")
    Observable<Result<List<FindMyPostListBean>>> findMyPostById(
            @Header("userid") int userid, @Header("sessionid") String sessionid,
            @Query("page") int page, @Query("count") int count);

    /**
     * 删除帖子
     */
    @DELETE("community/verify/v1/deletePost")
    Observable<Result> deletePost(
            @Header("userid") int userid, @Header("sessionid") String sessionid,
            @Query("communityId") String communityId);

    /**
     * 用户修改
     */
    @PUT("user/verify/v1/modifyUserPwd")
    Observable<Result> modifyUserPwd(
            @Header("userid") int userid, @Header("sessionid") String sessionid,
            @Query("oldPwd") String oldPwd, @Query("newPwd") String newPwd);


    /**
     * 绑定微信
     */
    @POST("user/verify/v1/bindWeChat")
    @FormUrlEncoded
    Observable<Result> bindWeChat(
            @Header("userid") int userid, @Header("sessionid") String sessionid,
            @Field("code") String code);

    /**
     * 取消关注
     */
    @DELETE("user/verify/v1/cancelFollow")
    Observable<Result> cancelFollow(
            @Header("userid") int userid, @Header("sessionid") String sessionid,
            @Query("focusId") int focusId);

    /**
     * 查询是否绑定微信号
     */
    @GET("user/verify/v1/whetherToBindWeChat")
    Observable<Result> whetherToBindWeChat(@Header("userid") int userid, @Header("sessionid") String sessionid);

    /**
     * 关注用户
     */
    @POST("user/verify/v1/addFollow")
    Observable<Result> addFollow(@Header("userid") int userid,
                                 @Header("sessionid") String sessionid,
                                 @Query("focusId") int focusId);

    /**
     * 查询好友信息
     */
    @GET("user/verify/v1/queryFriendInformation")
    Observable<Result<QueryFriendInformationBean>> queryFriendInformation(@Header("userid") int userid,
                                                                          @Header("sessionid") String sessionid,
                                                                          @Query("friend") int friend);

    /**
     * 取消点赞
     */
    @DELETE("community/verify/v1/cancelCommunityGreat")
    Observable<Result> cancelCommunityGreat(@Header("userid") int userid,
                                            @Header("sessionid") String sessionid,
                                            @Query("communityId") int communityId);

    /**
     * 创建自定义好友分组
     *
     * @param userId
     * @param sessionId
     * @param groupName
     * @return
     */
    @POST("chat/verify/v1/addFriendGroup")
    @FormUrlEncoded
    Observable<Result> addFriendGroup(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("groupName") String groupName);

    /**
     * 删除好友聊天记录
     *
     * @param userId
     * @param sessionId
     * @param friendUid
     * @return
     */
    @DELETE("chat/verify/v1/deleteChatRecord")
    Observable<Result> deleteChatRecord(@Header("userId") int userId,
                                        @Header("sessionId") String sessionId,
                                        @Query("friendUid") int friendUid);

    /**
     * 查询个人群
     *
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("chat/verify/v1/findFriendGroupList")
    Observable<Result<List<FriendGroup>>> findFriendGroupList(@Header("userId") int userId,
                                                              @Header("sessionId") String sessionId);

    /**
     * .删除用户好友分组
     *
     * @return
     */
    @DELETE("chat/verify/v1/deleteFriendGroup")
    Observable<Result> deleteFriendGroup(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("groupId") int groupId);

    /**
     * .修改好友备注
     *
     * @return
     */
    @PUT("chat/verify/v1/modifyFriendRemark")
    Observable<Result> modifyFriendRemark(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("friendUid") int friendUid,
                                          @Query("remarkName") String remarkName);

    /**
     * .修改好友分组名称
     *
     * @return
     */
    @PUT("chat/verify/v1/modifyGroupName")
    Observable<Result> modifyGroupName(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId,
                                       @Query("groupId") int groupId,
                                       @Query("groupName") String groupName);

    //绑定
    @PUT("user/verify/v1/bindingFaceId")
    Observable<Result> bindingFaceId(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Query("featureInfo") String featureInfo);

    //人脸识别登陆
    @FormUrlEncoded
    @POST("user/v1/faceLogin")
    Observable<Result<LoginUserInfoBean>> faceLogin(@Field("faceId") String time);


    /**
     * .根据环信userNames批量查询会话列表需要的用户信息
     *
     * @return
     */
    @GET("user/verify/v1/findConversationList")
    Observable<Result<List<FindConversationList>>> findConversationList(@Header("userId") int userId,
                                                                        @Header("sessionId") String sessionId,
                                                                        @Query("userNames") String userNames);

    /**
     * 移出群成员(管理员与群主才有的权限)
     *
     * @param userId
     * @param sessionId
     * @param friendUid
     * @param groupUserId
     * @return
     */
    @DELETE("group/verify/v1/removeGroupMember")
    Observable<Result> removeGroupMember(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("groupId") int friendUid,
                                         @Query("groupUserId") int groupUserId);

    /**
     * 查询群组内所有用户信息
     *
     * @param userId
     * @param sessionId
     * @param groupId
     * @return
     */
    @GET("group/verify/v1/findGroupMemberList")
    Observable<Result<List<GroupMember>>> findGroupMemberList(@Header("userId") int userId,
                                                              @Header("sessionId") String sessionId,
                                                              @Query("groupId") int groupId);

    /**
     * 调整群成员角色(群主才有的权限)
     *
     * @param userId
     * @param sessionId
     * @param groupId
     * @param groupUserId
     * @param role
     * @return
     */
    @PUT("group/verify/v1/modifyPermission")
    @FormUrlEncoded
    Observable<Result> modifyPermission(@Header("userId") int userId,
                                        @Header("sessionId") String sessionId,
                                        @Field("groupId") int groupId,
                                        @Field("groupUserId") int groupUserId,
                                        @Field("role") int role);

    /**
     * 批量邀请加群
     *
     * @return
     */
    @FormUrlEncoded
    @POST("group/verify/v1/batchInviteAddGroup")
    Observable<Result> batchInviteAddGroup(@Header("userId") int userId,
                                           @Header("sessionId") String sessionId,
                                           @Field("groupId") int groupId,
                                           @Field("receiverUids") int[] receiverUids);
    @FormUrlEncoded
    @PUT("group/verify/v1/modifyGroupDescription")
    Observable<Result> modifyGroupDescription(@Header("userId") int userId,
                                              @Header("sessionId") String sessionId,
                                              @Field("groupId") int groupId,
                                              @Field("description")String description);
    @FormUrlEncoded
    @POST("group/verify/v1/uploadGroupHeadPic")
    Observable<Result> uploadGroupHeadPic(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Field("groupId") int groupId,
                                          @Body MultipartBody body);
}
