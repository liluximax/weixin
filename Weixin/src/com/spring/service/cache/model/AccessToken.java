package com.spring.service.cache.model;

/**
 * Created by luc on 16/6/23.
 */
public class AccessToken {

    private  String token;

    private long createTime;

    private  long expireTime;

    public AccessToken(){
        this.token = "inital";
        this.createTime = 0;
        this.expireTime = 0;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
