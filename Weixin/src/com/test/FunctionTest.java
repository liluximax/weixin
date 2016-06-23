package com.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baidu.calculate.GetDistance;
import com.baidu.util.GetCityByLocate;
import com.google.common.collect.Lists;
import com.google.common.primitives.Bytes;
import com.spring.dao.AccessTokenDao;
import com.spring.dao.StationListDao;
import com.weixin.model.token.AccessToken;
import com.weixin.util.WeixinUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FunctionTest {

	public static void main(String[] args) {
		// double num = 2.37;
		// System.out.println(Math.ceil(num));
		// System.out.println(Math.floor(num));
		// System.out.println(Math.round(num));
		// System.out.println();
		// System.out.println(String.format("%1$.1f", num));

		/*
		 * LinkedList<Integer> list = Lists.newLinkedList();
		 * list.addAll(Arrays.asList(1,2,3)); System.out.println(list.poll());
		 * System.out.println(list.poll()); System.out.println(list.poll());
		 * System.out.println(list.poll());
		 */

		// String string = "1.2234332";
		// double num1 = Double.valueOf(string).doubleValue();
		// double num2 = Double.parseDouble(string);
		// System.out.println("第一种方式:"+num1+" 第二种方式:"+num2);
		// double sum = num1 + num2;
		// System.out.println("sum:"+sum);

		// int[] a = {49,38,65,97,76,13,27,49,78,34,12,64,1};
		// for(int i = 1; i < a.length; i++){
		// int temp = a[i];
		// int j;
		// for(j = i-1; j >= 0; j--){
		// if(a[j] > temp){
		// a[j+1] = a[j];
		// }
		// else{
		// break;
		// }
		// }
		// a[j+1] = temp;
		// }
		//
		// for (int i : a) {
		// System.out.print(i+",");
		// }

		// List<Map<String, String>> data = new ArrayList<>();
		// Map<String, String> single1 = new LinkedHashMap<>();
		// single1.put("station_id", "001");
		// single1.put("name", "a");
		// single1.put("distance", "0.4");
		//
		// Map<String, String> single2 = new LinkedHashMap<>();
		// single2.put("station_id", "002");
		// single2.put("name", "b");
		// single2.put("distance", "0.1");
		//
		// data.add(single1);
		// data.add(single2);
		//
		// List<Map<String, String>> result = GetDistance.sortByDistance(data);
		// System.out.println(result.toString());

		 ApplicationContext context = new
		 ClassPathXmlApplicationContext("com/spring/config/bean.xml");
		 StationListDao listDao =
		 context.getBean("stationDao",StationListDao.class);
		 List<Map<String, String>> result = listDao.getStationByCity("北京市");
		 System.out.println(result.toString());
//		String city = "北京市";
	}
}
