package com.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.spring.service.cache.AccessTokenCache;
import com.spring.service.cache.JsApiTicketCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.service.AccessTokenService;
import com.weixin.model.token.AccessToken;
import com.weixin.model.token.JsSignature;
import com.weixin.service.PayService;
import com.weixin.util.ModelMessageUtil;
import com.weixin.util.WeixinUtil;
import com.weixin.util.jssdk.JsUtil;

import net.sf.json.JSONObject;

public class WeixinTest {
	public static void main(String[] args) {
		
		//access_token
		ApplicationContext context = new ClassPathXmlApplicationContext("com/spring/config/bean.xml");
		AccessTokenCache service = context.getBean("access_token_cache",AccessTokenCache.class);
		String token = service.getAccessTokenByCache();
		System.out.println(token);
		/*
		 * jssdk验证
		 */
//		JsUtil util = context.getBean("jsutil",JsUtil.class);
//		JsSignature signature = util.sign("http://115.29.51.206/Weixin/add.jsp");
//		System.out.println(signature.getSignature());
		
//		String filePath = "/home/liluxi/Downloads/pic2.jpg";
//		String type = "thumb";
//		try {
//			WeixinUtil.upload(filePath, token.getToken(), type);
//		} catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/**
		 * 创建菜单
		 */
		String menu = JSONObject.fromObject(WeixinUtil.initMenu3()).toString();
		int result = WeixinUtil.creatMenu(token, menu);
		if(result == 0){
			System.out.println("菜单创建成功");
		}
		else{
			System.out.println("错误码是: " + result);
		}
		
		/**
		 * 查询菜单
		 */
//		System.out.println(WeixinUtil.queryMenu(token.getToken()));
		
		/** 
		 * 设置行业信息 及查询
		 */
//		ModelMessageUtil.setIndustry(token.getToken(), "11", "12");
//		System.out.println(ModelMessageUtil.queryIndustry(token.getToken()));
		
		/**
		 * 支付消息推送
		 */
//		PayService payService = new PayService();
//		String returncode = payService.sendPayMessage("oAC51s6M-XKhjKHbMyApTMB3gm64");
//		System.out.println(returncode);
	}

}
