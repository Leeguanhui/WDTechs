package com.wd.tech.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：古祥坤 on 2019/2/18 15:21
 * 邮箱：1724959985@qq.com
 */
public class Result <T> {
    String status;
    String message;
    T result;
    int groupId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
