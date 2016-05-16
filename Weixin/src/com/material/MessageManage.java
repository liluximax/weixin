package com.material;

import com.weixin.util.MessageUtil;

public class MessageManage {
	public String SubscribeMes(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("###欢迎关注###\n\n");
		buffer.append("回复 任意，返回相同的回复\n\n");
		buffer.append("按 1  麻将房费计算\n");
		buffer.append("按 2  返回菜单2\n");
		buffer.append("按 3  图文消息测试\n");
		buffer.append("按 4  会得到一张图片\n\n");
		buffer.append("发送地理位置 得到经纬度消息\n\n");
		buffer.append("按 ?  返回主菜单");
		return buffer.toString();
	}
	
	public String UcouponSub(){
		StringBuffer buffer = new StringBuffer();
//		buffer.append("###欢迎关注###\n\n");
//		buffer.append("欢迎语");
		buffer.append("优加油--移动互联网加油利器");
		return buffer.toString();
	}
	
	public String Menu2(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("业务介绍\n\n");
		buffer.append("介绍内容");
		return buffer.toString();
	}
	
	public String Menu3(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("获取App\n");
		buffer.append("");
		return buffer.toString();
	}
	
	public String AutoMessage(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("收到您的反馈");
		return buffer.toString();
	}
	
	public String MajiangIndrucion(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("麻将房费计算\n\n");
		buffer.append("两种计算方法：\n");
		buffer.append("1是摊房费不赢钱\n2是兑钱买牌，结账时，在兑的钱里扣除房费后，再按输赢比例分剩下的\n\n");
		buffer.append("规则：类型 东 南 西 北 房费 \n中间空格划分\n\n");
		buffer.append("输入例子：1 8 12 5 15 50\n代表按第一种算法计算，依次输入东 南 西 北最后的牌数\n\n");
		buffer.append("一定要按上面规则输入，否则得不到结果\n");
		return buffer.toString();
	}
	
	public String repeat(String toUserName, String fromUserName, String content){
		StringBuffer contentTemp = new StringBuffer();
		contentTemp.append("你刚才说了:\n\n" + content);
		contentTemp.append("\n\n");
		contentTemp.append("我说得对吧！\n\n");
		contentTemp.append("按？试试");
		return MessageUtil.initText(toUserName, fromUserName, contentTemp.toString());
	}
}
