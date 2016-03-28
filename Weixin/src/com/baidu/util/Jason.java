package com.baidu.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.util.WeixinUtil;

import net.sf.json.JSON;

/**
 * Servlet implementation class Jason
 */
@WebServlet("/com/baidu/util/Jason")
public class Jason extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jason() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
