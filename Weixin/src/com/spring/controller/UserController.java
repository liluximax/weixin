package com.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.weixin.util.WeixinUtil;
import net.sf.json.JSON;

@Controller
@RequestMapping("spring")
public class UserController {
	
	@RequestMapping(value="/changeJson")
	public void doJson(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String url = "http://app1.u-coupon.cn:8000/weixin/get_station_list.php?city=city_name";
		String city = request.getParameter("city");
		System.out.println(city);
		url = url.replace("city_name", city);
		System.out.println(url);
		JSON data = WeixinUtil.doGetStr(url);
		PrintWriter out = response.getWriter();
		out.print(data);
	}
	
	@RequestMapping(value="test")
	public void test(){
		System.out.println("success");
	}
}
