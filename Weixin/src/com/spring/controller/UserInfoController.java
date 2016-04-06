package com.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.util.WeixinUtil;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="userinfo")
public class UserInfoController {
	
	
	private static final String ACCESS_TOKEN_URL_WEB = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	private static final String APPID = "wxefdf75446a6bedc4";
	
	private static final String APPSECRET = "1305e7a3a69400ac17f96bef78e2d1e6";
	
	private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	@RequestMapping(value="getuserinfo")
	public void getCode(@RequestParam("code") String _code, 
						@RequestParam(value = "state", required = false) String _state,
						HttpServletResponse response) throws IOException{
		String code = _code;
		String state = _state;
		/**
		 * 通过code换取网页授权access_token
		 * 
		 */
		String access_token_url = ACCESS_TOKEN_URL_WEB.replace("APPID", APPID)
													  .replace("SECRET", APPSECRET).
													   replace("CODE", code);
		JSONObject access_token_data = WeixinUtil.doGetStr(access_token_url);
		String access_token = access_token_data.getString("access_token");
		String refresh_token = access_token_data.getString("refresh_token");
		String openid = access_token_data.getString("openid");
		String scope = access_token_data.getString("scope");
//		System.out.println(access_token+","+openid);
		/**
		 * 拉取用户信息
		 * 
		 */
		String user_info_url = USER_INFO_URL.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		JSON userInfo = WeixinUtil.doGetStr(user_info_url);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(userInfo);
//		String nickname = userInfo.getString("nickname");
//		String headimgurl = userInfo.getString("headimgurl");
//		System.out.println(nickname+","+headimgurl);
	}
} 
