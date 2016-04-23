package com.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baidu.calculate.GetDistance;
import com.baidu.util.GetCityByLocate;
import com.spring.dao.StationListDao;
import com.weixin.util.WeixinUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("station")
public class StationController {
	
	@Autowired
	private GetDistance calculate;
	
	@Autowired
	private StationListDao stationListDao;
	
	
	@RequestMapping(value="/changeJson")
	public void doJson(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String url = "http://115.29.51.206/Weixin/station/station_list.do?city=city_name";
//		String url = "http://app1.u-coupon.cn:8000/weixin/get_station_list.php?city=city_name";
		String lng = request.getParameter("lng");
		String lat = request.getParameter("lat");
		System.out.println("不带距离的controler得到的数据:"+lng+","+lat);
		String city = GetCityByLocate.getCityByLoc(lng, lat);
		url = url.replace("city_name", city);
		JSON data = WeixinUtil.doGetStr(url);
		PrintWriter out = response.getWriter();
		out.print(data);
	}
	
	@RequestMapping(value="test")
	public void test(){
		System.out.println("success");
	}
	
	@RequestMapping("calculate")
	public void doCalculate(@RequestParam(value = "city", required = false) String city,
							@RequestParam("lng") String lng, 
							@RequestParam("lat") String lat,
							HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("calculate controller中收到数据"+city+","+","+lng+","+lat);
		if(city == null){
			city = GetCityByLocate.getCityByLoc(lng, lat);
		}
		JSON data = calculate.calDistance(city, lng, lat);
		out.print(data);
	}
	
	@RequestMapping("calTest")
	public void doCalculateTest(@RequestParam("lng") String lng,
								@RequestParam("lat") String lat,
								@RequestParam("lngLoc") String lng_loc,
								@RequestParam("latLoc") String lat_loc){
		System.out.println(lng+","+lat+" "+lng_loc+","+lat_loc);
	}
	
	@RequestMapping("city")
	public void getCityList(HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String url = "http://115.29.51.206/Weixin/station/city_list.do";
//		String url = "http://app1.u-coupon.cn:8000/weixin/get_city_list.php";
		JSON cityData = WeixinUtil.doGetStr(url);
		out.print(cityData);
	}
	
	@RequestMapping("station_list")
	public void getStationList(@RequestParam(value = "city", required = false) String city,
							   HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		city = new String(city.getBytes("iso8859-1"), "utf-8");
		List<Map<String, String>> list = stationListDao.getStationByCity(city);
		Map<String, List<Map<String, String>>> result = new HashMap<>();
		if(list.size() != 0){
			result.put("station_list", list);
			JSON json = JSONObject.fromObject(result);
			out.print(json);
		}
		else {
			out.print("null");
		}
	}
	
	@RequestMapping("city_list")
	public void getCityListOrigin(HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		List<Map<String, String>> list = stationListDao.getCityList();
		Map<String, List<Map<String, String>>> result = new HashMap<>();
		result.put("city_list", list);
		JSON json = JSONObject.fromObject(result);
		out.print(json);
	}
}
