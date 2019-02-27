package com.wd.tech.bean;

/**
 * 作者：古祥坤 on 2019/2/27 19:31
 * 邮箱：1724959985@qq.com
 */
public class UserIntegralListBean {
    private int direction;
    private int type;
    private int amount;
    private long createTime;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
