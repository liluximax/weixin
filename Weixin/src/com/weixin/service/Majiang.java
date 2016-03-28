package com.weixin.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Majiang {
	
	public static String payMessage(LinkedList<Integer> result){
		int type = result.poll();
		int east = result.poll();
		int south = result.poll();
		int west = result.poll();
		int north = result.poll();
		int chips = east + south + west + north;
		int roomPayMent = result.poll();
		Integer rate = result.poll();
		if(rate == null){
			rate = 1;
		}
		
		if(type == 1){
			double average = (double)roomPayMent/4;
			
			double averageOver = (double)roomPayMent/3;
			int chipsOver = 0;
			
			double eastPay = average - ((double)east/chips - 0.25)*roomPayMent;
			double southPay = average - ((double)south/chips - 0.25)*roomPayMent;
			double westPay = average - ((double)west/chips - 0.25)*roomPayMent;
			double northPay = average - ((double)north/chips - 0.25)*roomPayMent;
			
			if(east >= chips/2){
				eastPay = 0;
				chipsOver = chips - east;
				southPay = averageOver - ((double)south/chipsOver - 0.33)*roomPayMent;
				westPay = averageOver - ((double)west/chipsOver - 0.33)*roomPayMent;
				northPay = averageOver - ((double)north/chipsOver - 0.33)*roomPayMent;
				
				if(south > 0.66*chipsOver){
					southPay = 0;
					westPay = ((double)north/(chipsOver - south))*roomPayMent;
					northPay = roomPayMent - westPay;
				}
				
				if(west > 0.66*chipsOver){
					westPay = 0;
					southPay = ((double)north/(chipsOver - west))*roomPayMent;
					northPay = roomPayMent - southPay;
				}
				
				if(north > 0.66*chipsOver){
					northPay = 0;
					westPay = ((double)south/(chipsOver - north))*roomPayMent;
					southPay = roomPayMent - westPay;
				}
			}
			
			if(south >= chips/2){
				southPay = 0;
				chipsOver = chips - south;
				eastPay = averageOver - ((double)east/chipsOver - 0.33)*roomPayMent;
				westPay = averageOver - ((double)west/chipsOver - 0.33)*roomPayMent;
				northPay = averageOver - ((double)north/chipsOver - 0.33)*roomPayMent;
				
				if(east > 0.66*chipsOver){
					eastPay = 0;
					westPay = ((double)north/(chipsOver - east))*roomPayMent;
					northPay = roomPayMent - westPay;
				}
				
				if(west > 0.66*chipsOver){
					westPay = 0;
					eastPay = ((double)north/(chipsOver - west))*roomPayMent;
					northPay = roomPayMent - eastPay;
				}
				
				if(north > 0.66*chipsOver){
					northPay = 0;
					eastPay = ((double)west/(chipsOver - north))*roomPayMent;
					westPay = roomPayMent - eastPay;
				}
			}
			
			if(west >= chips/2){
				westPay = 0;
				chipsOver = chips - west;
				eastPay = averageOver - ((double)east/chipsOver - 0.33)*roomPayMent;
				southPay = averageOver - ((double)south/chipsOver - 0.33)*roomPayMent;
				northPay = averageOver - ((double)north/chipsOver - 0.33)*roomPayMent;
				
				if(east > 0.66*chipsOver){
					eastPay = 0;
					southPay = ((double)north/(chipsOver - east))*roomPayMent;
					northPay = roomPayMent - southPay;
				}
				
				if(south > 0.66*chipsOver){
					southPay = 0;
					eastPay = ((double)north/(chipsOver - south))*roomPayMent;
					northPay = roomPayMent - eastPay;
				}
				
				if(north > 0.66*chipsOver){
					northPay = 0;
					eastPay = ((double)south/(chipsOver - north))*roomPayMent;
					southPay = roomPayMent - eastPay;
				}
			}
			
			if(north >= chips/2){
				northPay = 0;
				chipsOver = chips - north;
				eastPay = averageOver - ((double)east/chipsOver - 0.33)*roomPayMent;
				southPay = averageOver - ((double)south/chipsOver - 0.33)*roomPayMent;
				westPay = averageOver - ((double)west/chipsOver - 0.33)*roomPayMent;
				
				if(east > 0.66*chipsOver){
					eastPay = 0;
					southPay = ((double)west/(chipsOver - east))*roomPayMent;
					westPay = roomPayMent - southPay;
				}
				
				if(south > 0.66*chipsOver){
					southPay= 0;
					eastPay = ((double)west/(chipsOver - south))*roomPayMent;
					westPay = roomPayMent - eastPay;
				}
				
				if(west > 0.66*chipsOver){
					westPay = 0;
					eastPay = ((double)south/(chipsOver - west))*roomPayMent;
					southPay = roomPayMent - eastPay;
				}
			}
			
			Map<String, Long> payResult = new LinkedHashMap<>();
			payResult.put("东: ", Math.round(eastPay));
			payResult.put("南: ", Math.round(southPay));
			payResult.put("西: ", Math.round(westPay));
			payResult.put("北: ", Math.round(northPay));
			
			System.out.println("东 "+eastPay);
			System.out.println("南 "+southPay);
			System.out.println("西 "+westPay);
			System.out.println("北 "+northPay);
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("下面是东 南 西 北 应出房费:\n\n");
			buffer.append(payResult.toString());
			return buffer.toString();
		}
		
		else if(type == 2){
			int sum = (east + south + west + north)*rate - roomPayMent;
			double eastPay = ((double)east/chips)*sum;
			double southPay = ((double)south/chips)*sum;
			double westPay = ((double)west/chips)*sum;
			double northPay = ((double)north/chips)*sum;
			
			Map<String, Long> payResult = new LinkedHashMap<>();
			payResult.put("东: ", Math.round(eastPay));
			payResult.put("南: ", Math.round(southPay));
			payResult.put("西: ", Math.round(westPay));
			payResult.put("北: ", Math.round(northPay));
			
			Map<String, Long> payResultDatil = new LinkedHashMap<>();
			payResultDatil.put("东: ", Math.round(eastPay) - Math.round((double)chips*rate/4));
			payResultDatil.put("南: ", Math.round(southPay) - Math.round((double)chips*rate/4));
			payResultDatil.put("西: ", Math.round(westPay) - Math.round((double)chips*rate/4));
			payResultDatil.put("北: ", Math.round(northPay) - Math.round((double)chips*rate/4));
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("下面是东 南 西 北 从兑的钱堆里拿回的:\n\n");
			buffer.append(payResult.toString());
			buffer.append("\n\n下面是东 南 西 北 盈亏:\n\n");
			buffer.append(payResultDatil.toString());
			return buffer.toString();
		}
		
		else if(type == 3){
			
			int sum = (east + south + west + north)*rate;
			double eastPay = ((double)east/chips)*sum;
			double southPay = ((double)south/chips)*sum;
			double westPay = ((double)west/chips)*sum;
			double northPay = ((double)north/chips)*sum;
			
			Map<String, Long> payResult = new LinkedHashMap<>();
			payResult.put("东: ", Math.round(eastPay));
			payResult.put("南: ", Math.round(southPay));
			payResult.put("西: ", Math.round(westPay));
			payResult.put("北: ", Math.round(northPay));
			
			Map<String, Long> payResultDatil = new LinkedHashMap<>();
			payResultDatil.put("东: ", Math.round(eastPay) - Math.round((double)chips*rate/4) - Math.round((double)roomPayMent/4));
			payResultDatil.put("南: ", Math.round(southPay) - Math.round((double)chips*rate/4) - Math.round((double)roomPayMent/4));
			payResultDatil.put("西: ", Math.round(westPay) - Math.round((double)chips*rate/4) - Math.round((double)roomPayMent/4));
			payResultDatil.put("北: ", Math.round(northPay) - Math.round((double)chips*rate/4) - Math.round((double)roomPayMent/4));
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("下面是东 南 西 北 从兑的钱堆里拿回的:\n\n");
			buffer.append(payResult.toString());
			buffer.append("\n\n下面是东 南 西 北 盈亏:\n\n");
			buffer.append(payResultDatil.toString());
			return buffer.toString();
			
		}
		
		else{
			return "error: 前面少加类型了 1代表分摊房费不赢钱 2代表先兑钱买牌，再算钱，会赢钱";
		}

	}
}
