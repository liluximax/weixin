package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.AccessTokenDao;
import com.weixin.model.token.AccessToken;
import com.weixin.util.DeveloperId;
import com.weixin.util.WeixinUtil;
/*
此类已废弃
使用内存进行缓存
连接数据库的操作可供日后参考
 */
@Service("access_token_service")
public class AccessTokenService {
	
	@Autowired
	private AccessTokenDao accessTokenDao;
	
	public String getAccessToken(){
		
		long now = System.currentTimeMillis()/1000;
		
		String token_db = "";
		
		if(DeveloperId.server.equals("ali")){
			AccessToken tokenInDb = accessTokenDao.getaccess_token();
			
			long expire_time = tokenInDb.getExpire_time();
			token_db = tokenInDb.getToken();
			
			if(expire_time < now){
				String token = WeixinUtil.getAccessToken().getToken();
				accessTokenDao.insertAccessToken(token);
				token_db = token;
			}
		}
		else{
			AccessToken tokenInDb = accessTokenDao.getaccess_token_u();
			
			long expire_time = tokenInDb.getExpire_time();
			token_db = tokenInDb.getToken();
			
			if(expire_time < now){
				String token = WeixinUtil.getAccessToken().getToken();
				accessTokenDao.insertAccessToken_u(token);
				token_db = token;
			}
		}

		return token_db;
	}
	
	public String getAccessTokenDir(){
		return WeixinUtil.getAccessToken().getToken();
	}
}
