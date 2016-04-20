package com.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.service.AccessTokenService;
import com.weixin.model.token.AccessToken;
import com.weixin.service.PayService;
import com.weixin.util.ModelMessageUtil;
import com.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

public class WeixinTest {
	public static void main(String[] args) {
		
		//access_token
		ApplicationContext context = new ClassPathXmlApplicationContext("com/spring/config/bean.xml");
		AccessTokenService service = context.getBean("access_token_service",AccessTokenService.class);
		String token = service.getAccessToken();	
		System.out.println("token: " + token);
		
		
		
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
