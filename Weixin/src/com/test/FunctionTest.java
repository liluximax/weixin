package com.test;

import java.util.Arrays;
import java.util.LinkedList;

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
		
		
		String string = "1.2234332";
		double num1 = Double.valueOf(string).doubleValue();
		double num2 = Double.parseDouble(string);
		System.out.println("第一种方式:"+num1+" 第二种方式:"+num2);
		double sum = num1 + num2;
		System.out.println("sum:"+sum);
		
	}
}
