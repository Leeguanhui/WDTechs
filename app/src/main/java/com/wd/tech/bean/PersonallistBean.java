package com.wd.tech.bean;

/**
 * 作者：古祥坤 on 2019/2/18 20:13
 * 邮箱：1724959985@qq.com
 */
public class PersonallistBean {
    private String name;
    private int image;

    public PersonallistBean(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
