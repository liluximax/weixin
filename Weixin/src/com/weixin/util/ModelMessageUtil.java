package com.weixin.util;

import com.weixin.model.Industry;

import net.sf.json.JSONObject;



public class ModelMessageUtil {
	
	private static final String SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	
	private static final String QUERY_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
	
	public static int setIndustry(String token, String id1, String id2){
		int result = 0;
		String url = SET_INDUSTRY_URL.replace("ACCESS_TOKEN", token);
		
		Industry industry = new Industry();
		industry.setIndustry_id1(id1);
		industry.setIndustry_id2(id2);
		String industryInfro = JSONObject.fromObject(industry).toString();
		
		JSONObject jsonObject = WeixinUtil.doPostStr(url, industryInfro);
		
		if(jsonObject == null){
			
			return 0;
		}
		
		return 1;
		
	}
	
	public static String queryIndustry(String token){
		
		String url = QUERY_INDUSTRY_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		return JSONObject.fromObject(jsonObject).toString();
		
	}
}
