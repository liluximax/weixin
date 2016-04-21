package com.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.model.token.JsSignature;
import com.weixin.util.DeveloperId;
import com.weixin.util.WeixinUtil;
import com.weixin.util.jssdk.JsUtil;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="userinfo")
public class UserInfoController {
	
	@Autowired
	private JsUtil jsUtil;
	
	private static final String ACCESS_TOKEN_URL_WEB = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	@RequestMapping(value="getuserinfo")
	public void getCode(@RequestParam("code") String _code, 
						@RequestParam(value = "state", required = false) String _state,
						HttpServletResponse response,
						HttpSession session) throws IOException{
		String code = _code;
		String state = _state;
		/**
		 * 通过code换取网页授权access_token
		 * 
		 */
		String access_token_url = ACCESS_TOKEN_URL_WEB.replace("APPID", DeveloperId.APPID)
													  .replace("SECRET", DeveloperId.APPSECRET).
													   replace("CODE", code);
		JSONObject access_token_data = WeixinUtil.doGetStr(access_token_url);
		try{
			String access_token = access_token_data.getString("access_token");
			String refresh_token = access_token_data.getString("refresh_token");
			String openid = access_token_data.getString("openid");
			String scope = access_token_data.getString("scope");
//			System.out.println(access_token+","+openid);
			/**
			 * 拉取用户信息
			 * 
			 */
			String user_info_url = USER_INFO_URL.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
			JSON userInfo = WeixinUtil.doGetStr(user_info_url);
			JSONObject userInfoObj = JSONObject.fromObject(userInfo);
			String image = userInfoObj.getString("headimgurl");
			System.out.println("UserInfoController中打印的imageurl: "+image);
			session.setAttribute("imageurl", image);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(userInfo);
		}	
		catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
//		String nickname = userInfo.getString("nickname");
//		String headimgurl = userInfo.getString("headimgurl");
//		System.out.println(nickname+","+headimgurl);
	}
	
	@RequestMapping("jssdk")
	public void getJssdk(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String url = request.getParameter("url");
//		String url = "http://wxoa.u-coupon.cn/Weixin/jiayou/list.jsp";
//		String url = "http://115.29.51.206/Weixin/add.jsp";
		JsSignature signature = jsUtil.sign(url);
		JSONObject result = JSONObject.fromObject(signature);
		out.print(result.toString());
	}
} 
