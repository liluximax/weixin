package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.baidu.calculate.GetDistance;
import com.baidu.util.GetCityByLocate;
import com.google.common.collect.Lists;

public class FunctionTest {
	
	public static void main(String[] args) {
//		double num = 2.37;
//		System.out.println(Math.ceil(num));
//		System.out.println(Math.floor(num));
//		System.out.println(Math.round(num));
//		System.out.println();
//		System.out.println(String.format("%1$.1f", num));
		

/*		LinkedList<Integer> list = Lists.newLinkedList();
		list.addAll(Arrays.asList(1,2,3));
		System.out.println(list.poll());
		System.out.println(list.poll());
		System.out.println(list.poll());
		System.out.println(list.poll());*/
		
		
//		String string = "1.2234332";
//		double num1 = Double.valueOf(string).doubleValue();
//		double num2 = Double.parseDouble(string);
//		System.out.println("第一种方式:"+num1+" 第二种方式:"+num2);
//		double sum = num1 + num2;
//		System.out.println("sum:"+sum);
		
//		int[] a = {49,38,65,97,76,13,27,49,78,34,12,64,1};
//		for(int i = 1; i < a.length; i++){
//			int temp = a[i];
//			int j;
//			for(j = i-1; j >= 0; j--){
//				if(a[j] > temp){
//					a[j+1] = a[j];
//				}
//				else{
//					break;
//				}
//			}
//			a[j+1] = temp;
//		}
//		
//		for (int i : a) {
//			System.out.print(i+",");
//		}
		
//		List<Map<String, String>> data = new ArrayList<>();
//		Map<String, String> single1 = new LinkedHashMap<>();
//		single1.put("station_id", "001");
//		single1.put("name", "a");
//		single1.put("distance", "0.4");
//		
//		Map<String, String> single2 = new LinkedHashMap<>();
//		single2.put("station_id", "002");
//		single2.put("name", "b");
//		single2.put("distance", "0.1");
//		
//		data.add(single1);
//		data.add(single2);
//		
//		List<Map<String, String>> result = GetDistance.sortByDistance(data);
//		System.out.println(result.toString());
		
		String city = GetCityByLocate.getCityByLoc("116.36483899787", "39.969649077248");
		System.out.println(city);
	}
}
