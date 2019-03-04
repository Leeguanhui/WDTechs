package com.wd.tech.bean;

import java.util.List;

public class QueryFriendInformationBean {
    /**
     * birthday : 950112000000
     * email : 3256497513@qq.com
     * headPic : http://mobile.bwstudent.com/images/tech/default/tech.jpg
     * integral : 130
     * myGroupList : []
     * nickName : 咿呀
     * phone : 17701283111
     * sex : 2
     * signature : 愿你任何时候都不缺从头再来的勇气
     * userId : 20
     * userName : 3d3Pd017701283111
     * whetherFaceId : 2
     * whetherVip : 2
     */

    private long birthday;
    private String email;
    private String headPic;
    private int integral;
    private String nickName;
    private String phone;
    private int sex;
    private String signature;
    private int userId;
    private String userName;
    private int whetherFaceId;
    private int whetherVip;
    private List<?> myGroupList;

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWhetherFaceId() {
        return whetherFaceId;
    }

    public void setWhetherFaceId(int whetherFaceId) {
        this.whetherFaceId = whetherFaceId;
    }

    public int getWhetherVip() {
        return whetherVip;
    }

    public void setWhetherVip(int whetherVip) {
        this.whetherVip = whetherVip;
    }

    public List<?> getMyGroupList() {
        return myGroupList;
    }

    public void setMyGroupList(List<?> myGroupList) {
        this.myGroupList = myGroupList;
    }
}
