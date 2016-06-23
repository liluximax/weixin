package com.spring.service.cache;

import com.spring.service.cache.model.AccessToken;
import com.weixin.util.WeixinUtil;
import org.springframework.stereotype.Service;

/**
 * Created by luc on 16/6/23.
 */
@Service("access_token_cache")
public class AccessTokenCache {

    private static AccessToken accessToken = new AccessToken();

    public String getAccessTokenByCache(){
        long now = System.currentTimeMillis() / 1000;
        long expire = accessToken.getExpireTime();
        String token = new String();
        if(now < expire){
            token = accessToken.getToken();
            System.out.println("使用缓存accessToken");
        }
        else {
            token = WeixinUtil.getAccessToken().getToken();
            System.out.println("重新获取accessToken");
            expire = now + 3600;
            accessToken.setToken(token);
            accessToken.setCreateTime(now);
            accessToken.setExpireTime(expire);
        }
        return token;
    }
}
