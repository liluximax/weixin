package com.weixin.util;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.common.collect.Maps;
import com.sun.accessibility.internal.resources.accessibility;
import com.thoughtworks.xstream.XStream;
import com.weixin.model.message.Image;
import com.weixin.model.message.ImageMessage;
import com.weixin.model.message.MusciMessage;
import com.weixin.model.message.Music;
import com.weixin.model.message.News;
import com.weixin.model.message.NewsMessage;
import com.weixin.model.message.TextMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_CLIK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";
	public static final String EVENT_SCANCODE_PUSH = "scancode_push";
	public static final String EVENT_SCANCODE_WAITMSG = "scancode_waitmsg";

	/**
	 * xml -> map 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = Maps.newLinkedHashMap();
 		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> childElements = root.elements();
		for(Element child : childElements){
			map.put(child.getName(), child.getText());
			List<Element> elementsList = child.elements();
			for (Element ele : elementsList) {
				map.put(ele.getName(), ele.getText());
			}
		}
		ins.close();
		return map;
}
	/**
	 * 生成text消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return MessageUtil.textMessageToXml(text);
	}
	public static String initNewsMessage(String toUserName, String fromUserName){
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		
		List<News> newsList = new ArrayList<>();
		News news= new News();
		news.setTitle("Happy New Year");
		news.setDescription("#####测试#####");
		news.setPicUrl("http://115.29.51.206/Weixin/image/newYear.jpg");
		news.setUrl("www.baidu.com");
		newsList.add(news);
		News news2 = new News();
		news2.setTitle("Happy New Year");
		news2.setDescription("#####测试#####");
		news2.setPicUrl("http://115.29.51.206/Weixin/image/newYear.jpg");
		news2.setUrl("www.sogou.com");
		newsList.add(news2);

		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		return newsMessageToXml(newsMessage);
	}
	
	public static String initImageMessage(String toUserName, String fromUserName, String MediaId){
		ImageMessage imageMessage = new ImageMessage();
		Image image = new Image();
		image.setMediaId(MediaId);
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		return imageMessageToXml(imageMessage);
	}
	
	public static String initMusicMessage(String toUserName, String fromUserName){
		MusciMessage message = new MusciMessage();
		Music music = new Music();
		music.setTitle("小幸运");
		music.setDescription("######");
		music.setMusicUrl("www.baidu.com");
		
		message.setFromUserName(toUserName);
		message.setToUserName(fromUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(MESSAGE_MUSIC);
		message.setMusic(music);
		return musicMessageToXml(message);
	}
	public static String textMessageToXml(TextMessage textMessage){
		 XStream xstream = new XStream();
		 xstream.alias("xml", textMessage.getClass());
		 return xstream.toXML(textMessage);
		
	}
	/**
	 * 图文消息转xml
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		xstream.alias("item", new Image().getClass());
		return xstream.toXML(imageMessage);
	}
	
	public static String musicMessageToXml(MusciMessage musciMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musciMessage.getClass());
		xstream.alias("item", new Music().getClass());
		return xstream.toXML(musciMessage);
	}
}
