package com.wd.tech.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：古祥坤 on 2019/2/19 13:36
 * 邮箱：1724959985@qq.com
 */
@Entity
public class LoginUserInfoBean {
    /**
     * nickName : 徐云杰
     * phone : 18600151568
     * pwd : R+0jdN3P4MXHPMFVe9cX5MbX5ulIXHJkfigPLKEeTBY5lUgxJWUNg0js1oGtbsKiLFL4ScqdmUbtHXIfrgQnWrwTNjf09OJLycbeJ+ka4+CV7I1eEqG8DtZPnQoCyxjoYMjO4soDl6EX9YgqaZp3DlUH4pXrYHYz58YyFkSeJEk=
     * sessionId : 15372410025241007
     * userId : 1007
     * userName : 4Vg15Z18600151568
     * whetherVip : 2
     * whetherFaceId : 1
     */
    @Id(autoincrement = true)
    private long gid;
    private String nickName;
    private String phone;
    private String pwd;
    private String sessionId;
    private int userId;
    private String userName;
    private String headPic;
    private int whetherVip;
    private int whetherFaceId;
    private int statu;
    @Generated(hash = 1471506856)
    public LoginUserInfoBean(long gid, String nickName, String phone, String pwd, String sessionId, int userId, String userName, String headPic, int whetherVip, int whetherFaceId, int statu) {
        this.gid = gid;
        this.nickName = nickName;
        this.phone = phone;
        this.pwd = pwd;
        this.sessionId = sessionId;
        this.userId = userId;
        this.userName = userName;
        this.headPic = headPic;
        this.whetherVip = whetherVip;
        this.whetherFaceId = whetherFaceId;
        this.statu = statu;
    }
    @Generated(hash = 1943470081)
    public LoginUserInfoBean() {
    }
    public long getGid() {
        return this.gid;
    }
    public void setGid(long gid) {
        this.gid = gid;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getHeadPic() {
        return this.headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public int getWhetherVip() {
        return this.whetherVip;
    }
    public void setWhetherVip(int whetherVip) {
        this.whetherVip = whetherVip;
    }
    public int getWhetherFaceId() {
        return this.whetherFaceId;
    }
    public void setWhetherFaceId(int whetherFaceId) {
        this.whetherFaceId = whetherFaceId;
    }
    public int getStatu() {
        return this.statu;
    }
    public void setStatu(int statu) {
        this.statu = statu;
    }

}
