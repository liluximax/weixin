package com.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.util.DeveloperId;

@Controller
@RequestMapping("pay")
public class Payment {
	
	@RequestMapping("getParam")
	public void getBrandWCPayRequest(HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		String appId = DeveloperId.APPID;
		long timeStamp = System.currentTimeMillis() / 1000;
		
	}
}
