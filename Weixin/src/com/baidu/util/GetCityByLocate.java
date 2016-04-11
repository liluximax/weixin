package com.baidu.util;
import com.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;

public class GetCityByLocate {
	
	private static final String API_BAIDU_URL = "http://api.map.baidu.com/geocoder/v2/?ak=AK&location=LOCATION&output=json";
	
	private static final String ak = "vH0fPZRoiGefNfrcbBa4rHRZ";
	
	public static String getCityByLoc(String lng, String lat){
		String loc = "";
		loc = lat + "," + lng;
		String url = API_BAIDU_URL.replace("AK", ak).replace("LOCATION", loc);
		JSONObject data_orignal = WeixinUtil.doGetStr(url);
		JSONObject data_result = data_orignal.getJSONObject("result");
		JSONObject data_addressComponent = data_result.getJSONObject("addressComponent");
		String city = data_addressComponent.getString("city");
		return city;
	}
	
}
