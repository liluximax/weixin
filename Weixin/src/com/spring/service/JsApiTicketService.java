package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.dao.JsapiTicketDao;
import com.weixin.model.token.JsApiTicket;
import com.weixin.util.DeveloperId;
import com.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
/*
此类已废弃
使用内存进行缓存
连接数据库的操作可供日后参考
 */
@Service
public class JsApiTicketService {

	@Autowired
	private JsapiTicketDao jsapiTicketDao;
	
	@Autowired
	private AccessTokenService accessTokenService;
	
	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	private String getjsspi_ticket(){
		//使用缓存处理过的 access_token
		String access_token = accessTokenService.getAccessToken();
		String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
		JSONObject object = WeixinUtil.doGetStr(url);
		String ticket = object.getString("ticket");
		return ticket;
	}
	
	private String getjsspi_ticket2(){
		String access_token = accessTokenService.getAccessTokenDir();
		String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
		JSONObject object = WeixinUtil.doGetStr(url);
		String ticket = object.getString("ticket");
		return ticket;
	}
	
	public String getJsApiTicket(){
		
		long now = System.currentTimeMillis()/1000;
		String ticket_db = "";
		
		if(DeveloperId.server.equals("ali")){
			JsApiTicket jsApiTicketDb = jsapiTicketDao.getJsApiTicket();
			
			long expire_time = jsApiTicketDb.getExpire_time();
			ticket_db = jsApiTicketDb.getTicket();
			
			if(expire_time < now){
				String ticket = getjsspi_ticket();
				jsapiTicketDao.insertJsApiTicket(ticket);
				ticket_db = ticket;
			}
		}
		else {
			JsApiTicket jsApiTicketDb = jsapiTicketDao.getJsApiTicket_u();
			
			long expire_time = jsApiTicketDb.getExpire_time();
			ticket_db = jsApiTicketDb.getTicket();
			
			if(expire_time < now){
				String ticket = getjsspi_ticket();
				jsapiTicketDao.insertJsApiTicket_u(ticket);
				ticket_db = ticket;
			}
		}
		
		return ticket_db;
	}
	
	//不缓存直接调用jsapi_tiket
	public String getJsApiTicketDir(){
		return getjsspi_ticket2();
	}
	
}

