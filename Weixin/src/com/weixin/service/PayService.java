package com.weixin.service;

import com.weixin.model.pay.BaseContent;
import com.weixin.model.pay.PayData;
import com.weixin.model.pay.PayModelMessage;
import com.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

public class PayService {
	
	private final String PAYURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	public String sendPayMessage(String toUser){
		
		String token = WeixinUtil.getAccessToken().getToken();
		String url = PAYURL.replace("ACCESS_TOKEN", token);
		String message = initPayTemplate(toUser);
		JSONObject jsonObject = WeixinUtil.doPostStr(url, message);
		return jsonObject.toString();
	}
	
	private String initPayTemplate(String toUser){
		
		String templateId = "V_7PdWsQdFU0TQaHfLgxtKo5aJlYLc-52Oi3ow3B8KY";
		
		PayData payData = new PayData();
		BaseContent contentFirst = new BaseContent();
		contentFirst.setValue("恭喜你购买成功！");
		contentFirst.setColor("#173177");
		payData.setFirst(contentFirst);
		
		BaseContent contentProduct = new BaseContent();
		contentProduct.setValue("巧克力");
		contentProduct.setColor("#173177");
		payData.setProduct(contentProduct);
		
		BaseContent contentValue = new BaseContent();
		contentValue.setValue("39.8元");
		contentValue.setColor("#173177");
		payData.setPrice(contentValue);
		
		BaseContent contentTime = new BaseContent();
		contentTime.setValue("2014年9月22日");
		contentTime.setColor("#173177");
		payData.setTime(contentTime);
		
		BaseContent contentRemark = new BaseContent();
		contentRemark.setValue("欢迎再次购买！");
		contentRemark.setColor("#173177");
		payData.setRemark(contentRemark);
		
		
		PayModelMessage message = new PayModelMessage();
		message.setTouser(toUser);
		message.setTemplate_id(templateId);
		message.setUrl("http://weixin.qq.com/download");
		message.setData(payData);
		
		String finalMessage = JSONObject.fromObject(message).toString();
		System.out.println(finalMessage);
		return finalMessage;
	}

}
