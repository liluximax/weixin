package com.spring.service.cache;

import com.spring.service.cache.model.JsApiTicket;
import com.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luc on 16/6/23.
 */
@Service("jsapi_ticket__cache")
public class JsApiTicketCache {

    private static JsApiTicket jsApiTicket = new JsApiTicket();

    @Autowired
    private AccessTokenCache accessTokenCache;

    private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    private String getJsApiTicket(){
        String access_token = accessTokenCache.getAccessTokenByCache();
        String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
        JSONObject object = WeixinUtil.doGetStr(url);
        String ticket = object.getString("ticket");
        return ticket;
    }

    public String getJsApiTicketByCache(){
        long now = System.currentTimeMillis() / 1000;
        long expire = jsApiTicket.getExpireTime();
        String ticket = new String();
        if(now < expire){
            ticket = jsApiTicket.getTicket();
            System.out.println("使用缓存JsApiTicket");
        }
        else {
            ticket = getJsApiTicket();
            System.out.println("重新获取JsApiTicket");
            expire = now + 3600;
            jsApiTicket.setTicket(ticket);
            jsApiTicket.setCreateTime(now);
            jsApiTicket.setExpireTime(expire);
        }
        return ticket;
    }
}
