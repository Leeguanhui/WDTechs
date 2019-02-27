package com.wd.tech.bean;

/**
 * 作者：古祥坤 on 2019/2/27 17:28
 * 邮箱：1724959985@qq.com
 */
public class NotifiListBean {

    /**
     * content : 您加入的哈哈群,群主已申请解散
     * createTime : 1551254486000
     * id : 46
     * receiveUid : 3
     */

    private String content;
    private long createTime;
    private int id;
    private int receiveUid;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiveUid() {
        return receiveUid;
    }

    public void setReceiveUid(int receiveUid) {
        this.receiveUid = receiveUid;
    }
}
