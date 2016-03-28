package com.baidu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.model.Location;

public class ServletService extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		Location location = new Location();
		location.setLat(Double.parseDouble(req.getParameter("lat")));
		location.setLng(Double.parseDouble(req.getParameter("lng")));
		location.setId(Integer.parseInt(req.getParameter("id")));
		System.out.println("经度: " + location.getLat() + ",纬度: " + location.getLng() + ",id: " + location.getId());
		System.out.println(location.getLat()+location.getLng());
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html;charset=utf-8");
		out.print("经度: " + location.getLat() + ",纬度: " + location.getLng() + ",id: " + location.getId());
		double sum = location.getLat() + location.getLng();
		out.print("<br>" + sum);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
	
}
