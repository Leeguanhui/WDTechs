package com.wd.tech.bean;

import java.util.Map;

/**
 * 作者：夏洪武
 * 时间：2019/3/4.
 * 邮箱：
 * 说明：
 */
public class PayResult {
    private String result;
    private String resultStatus;
    public PayResult(Map<String, String> obj) {

    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getResultStatus() {
        return resultStatus;
    }
    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
}
