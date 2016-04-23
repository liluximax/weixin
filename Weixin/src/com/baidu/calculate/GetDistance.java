package com.baidu.calculate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.weixin.util.WeixinUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component("calculate")
public class GetDistance {
	
	private static final double EARTH_RADIUS = 6378137;
	
	private static double rad(double d){
		return d*Math.PI/180.0;
	}
	private static double GetDistanceMethod(double lng1, double lat1, double lng2, double lat2)
	{
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
    private static double changeDouble(Double dou){
        NumberFormat   nf=new  DecimalFormat( "0.0 "); 
        dou = Double.parseDouble(nf.format(dou));
        return dou;
    }
	
	public JSON calDistance(String _city, String _lng, String _lat){
		
		//如果定位城市无油站，包装一个null
		Map<String, String> station_null = new HashMap<>();
		station_null.put("station_list", "null");
		JSON statioin_list_json = JSONObject.fromObject(station_null);
		
		List<Map<String, String>> station_list = new LinkedList<>();
		String city = _city;
		double lng = Double.parseDouble(_lng);
		double lat = Double.parseDouble(_lat);
//		String url = "http://app1.u-coupon.cn:8000/weixin/get_station_list.php?city=city_name";
		String url = "http://115.29.51.206/Weixin/station/station_list.do?city=city_name";
		url = url.replace("city_name", city);
		JSON data = WeixinUtil.doGetStr(url);
		String data_string = data.toString();
		if(data_string != "null"){
			JSONObject list = JSONObject.fromObject(data);
			JSONArray listArray = list.getJSONArray("station_list");
			for(int i = 0; i < listArray.size(); i++){
				Map<String, String> single = new LinkedHashMap<>();
				JSONObject row = listArray.getJSONObject(i);
				String id = row.getString("station_id");
				String name = row.getString("name");
				String station_lat = row.getString("latitude");
				String station_lng = row.getString("longitude");
				String address =  row.getString("address");
				
				double station_lng_double = Double.parseDouble(station_lat);
				double station_lat_double = Double.parseDouble(station_lng);
				
				double[] station_loc_baidu = CoordinateTrans.wgs2bd(station_lat_double, station_lng_double);
				
				System.out.println("进入计算距离方法 ### 定位坐标: "+lng+","+lat+" 第"+i+"个油站修正后坐标"+station_loc_baidu[1]+","+station_loc_baidu[0]);
					
				double distance = GetDistanceMethod(lng, lat, station_loc_baidu[1], station_loc_baidu[0]);
				
				distance = changeDouble(distance/1000);
				
				single.put("station_id", id);
				single.put("name", name);
				single.put("longitude", station_lng);
				single.put("latitude", station_lat);
				single.put("address", address);
				single.put("distance", ""+distance);
				
				station_list.add(single);
				
			}
			Map station_list_map = new HashMap<>();
			List<Map<String, String>> station_list_sorted = sortByDistance(station_list);
			station_list_map.put("station_list", station_list_sorted);
			station_list_map.put("city",city);
			statioin_list_json = JSONObject.fromObject(station_list_map);
		}
		System.out.println(statioin_list_json.toString());
		return statioin_list_json;
	}
	
	private List<Map<String, String>> sortByDistance(List<Map<String, String>> data){
		for(int i = 1; i < data.size(); i++){
			Map<String, String> temp = new LinkedHashMap<>();
			temp = data.get(i);
			Double distance_temp = Double.parseDouble(temp.get("distance"));
			int j;
			for(j = i-1; j >= 0; j--){
				Double distance_array = Double.parseDouble(data.get(j).get("distance"));
				if(distance_array > distance_temp){
					data.remove(j+1);
					data.add(j+1, data.get(j));
				}
				else{
					break;
				}
			}
			data.remove(j+1);
			data.add(j+1, temp);
		}
		return data;
	}
}
