package com.wd.tech.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zxk
 * on 2019/3/6 14:10
 * QQ:666666
 * Describe:
 */
@Entity
public class FindConversationList  {

        /**
         * headPic : http://172.17.8.100/images/tech/head_pic/2018-10-08/20181008085110.jpg
         * nickName : 周考
         * pwd : R+0jdN3P4MXHPMFVe9cX5MbX5ulIXHJkfigPLKEeTBY5lUgxJWUNg0js1oGtbsKiLFL4ScqdmUbtHXIfrgQnWrwTNjf09OJLycbeJ+ka4+CV7I1eEqG8DtZPnQoCyxjoYMjO4soDl6EX9YgqaZp3DlUH4pXrYHYz58YyFkSeJEk=
         * userId : 1078
         * userName : kx30FD16619998889
         */
        @Id(autoincrement = true)
        private long userId;
        private String headPic;
        private String nickName;
        private String pwd;
        private String userName;
        @Generated(hash = 52308441)
        public FindConversationList(long userId, String headPic, String nickName, String pwd, String userName) {
            this.userId = userId;
            this.headPic = headPic;
            this.nickName = nickName;
            this.pwd = pwd;
            this.userName = userName;
        }
        @Generated(hash = 1653457)
        public FindConversationList() {
        }
        public long getUserId() {
            return this.userId;
        }
        public void setUserId(long userId) {
            this.userId = userId;
        }
        public String getHeadPic() {
            return this.headPic;
        }
        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }
        public String getNickName() {
            return this.nickName;
        }
        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        public String getPwd() {
            return this.pwd;
        }
        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
        public String getUserName() {
            return this.userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }


}
