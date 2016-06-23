package com.spring.service.cache.model;

/**
 * Created by luc on 16/6/23.
 */
public class JsApiTicket {

    private String ticket;

    private  long createTime;

    private  long expireTime;

    public JsApiTicket(){
        this.ticket = "inital";
        this.createTime = 0;
        this.expireTime = 0;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
