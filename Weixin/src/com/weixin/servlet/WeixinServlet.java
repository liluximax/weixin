package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.google.common.collect.Maps;
import com.material.MediaId;
import com.material.MessageManage;
import com.weixin.service.Majiang;
import com.weixin.service.PayService;
import com.weixin.util.CheckUtil;
import com.weixin.util.MessageUtil;

@WebServlet("/wxDo")
public class WeixinServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		PayService payService = new PayService();
		
		PrintWriter out = resp.getWriter();
		MessageManage matrial = new MessageManage();
		
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			System.out.println();
			System.out.println("!!!!!!!!!!");
			System.out.println("收到的数据包:  " + map.toString());
			System.out.println();
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String mediaId = map.get("MediaId");
			String location_X = map.get("Location_X");
			String location_Y = map.get("Location_Y");
			String scale = map.get("Scale");
			String label = map.get("Label");
			String url= map.get("Url");
			String event = map.get("Event");
			String eventKey = map.get("EventKey");
//			String scanCodeInfo = map.get("ScanCodeInfo");
//			System.out.println("ScanCodeInfo" + scanCodeInfo);
			/**
			 * 查看收到的消息
			 */
//			System.out.println("Content: " + content);
//			System.out.println("MsgType: " + msgType);
//			System.out.println("FromUserName: " + fromUserName);
//			System.out.println("ToUserName: " + toUserName);
//			System.out.println("CreateTime: " + createTime);
//			System.out.println("Location_X: " + location_X);
//			System.out.println("Location_Y: " + location_Y);
//			System.out.println("Scale: " + scale);
//			System.out.println("Label: " + label);
//			System.out.println("Url: " + url);
//			System.out.println("MediaId: " + mediaId);
			System.out.println("########");
			
			
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if((content.length() < 10) || content.length() > 20){
					
					if("1".equals(content)){
						message = MessageUtil.initText(toUserName, fromUserName, matrial.MajiangIndrucion());
					}
					else if("2".equals(content)){
						message = MessageUtil.initText(toUserName, fromUserName, matrial.Menu2());
					}
					else if(("?".equals(content)) || ("？".equals(content))){
						message = MessageUtil.initText(toUserName, fromUserName, matrial.SubscribeMes());
					}
					else if("3".equals(content)){
						message = MessageUtil.initNewsMessage(toUserName, fromUserName);
					}
					else if("4".equals(content)){
						message = MessageUtil.initImageMessage(toUserName, fromUserName, MediaId.imageId);
					}
					else if("5".equals(content)){
						String returncode = payService.sendPayMessage(fromUserName);
						System.out.println(returncode);
					}
					
					else {
						message = matrial.repeat(toUserName, fromUserName, content);
					}
				}
				else {
					LinkedList<Integer> result = new LinkedList<>();
					for(String o : content.split(" ")){
						try {
							int temp = Integer.parseInt(o);
							result.add(temp);
						} catch (Exception e) {
							
						}
					}
					if((result.size() == 6) || (result.size() == 7)){
						String payResult = Majiang.payMessage(result);
						message = MessageUtil.initText(toUserName, fromUserName, payResult);
					}
					else{
						message = matrial.repeat(toUserName, fromUserName, content);
					}
				}
				
			}
			
			if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(event)){
					message = MessageUtil.initText(toUserName, fromUserName, matrial.SubscribeMes());
				}
				else if(MessageUtil.EVENT_CLIK.equals(event)){
					if("3".equals(eventKey)){
						message = MessageUtil.initText(toUserName, fromUserName, matrial.MajiangIndrucion());
					}
					else if("24".equals(eventKey)){
						message = MessageUtil.initText(toUserName, fromUserName, matrial.SubscribeMes());
					}
					else if("23".equals(eventKey)){
						String returncode = payService.sendPayMessage(fromUserName);
						System.out.println(returncode);					}
					else{
						
					}
				}
				else if(MessageUtil.EVENT_SCANCODE_WAITMSG.equals(event)){
					 message = MessageUtil.initText(toUserName, fromUserName, map.get("ScanResult"));
				}
				else{
					System.out.println("no handle");
				}
			}
			
			if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
				message = MessageUtil.initText(toUserName, fromUserName, "你刚才发送的是图片");
			}
			
			if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				Map<String, String> location = Maps.newLinkedHashMap();
				location.put("位置", map.get("Label"));
				location.put("POI", map.get("Poiname"));
				location.put("经度", map.get("Location_X"));
				location.put("维度", map.get("Location_Y"));
				location.put("精度", map.get("Scale"));
				message = MessageUtil.initText(toUserName, fromUserName, location.toString());
			}
			
			
			System.out.println();
			System.out.println("发送的数据包如下");
			System.out.println(message);
			System.out.println("*****************");
			System.out.println();
			out.print(message);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}
	

}
