package com.test;

import com.weixin.model.token.JsApiTicket;
import com.weixin.model.token.JsSignature;
import com.weixin.util.jssdk.JsUtil;

public class Jiekou {
	
	String accessToken = "";
	
	public static void main(String[] args) {
		
		String url = "http://mp.weixin.qq.com?params=value";
		
		String AccessToken= JsUtil.getAccessToken().getToken();
		JsApiTicket ticket = JsUtil.getJsApiTicket(AccessToken);
		System.out.println("jsapi_ticket: " + ticket.getTicket());
		
		JsSignature jsSignature = JsUtil.getJsSignature(ticket.getTicket(), url);
		System.out.println("signature: " + jsSignature.getSignature());
		System.out.println("noncestr: " +jsSignature.getNoncestr());
		
		
		
	}
	
	
}
