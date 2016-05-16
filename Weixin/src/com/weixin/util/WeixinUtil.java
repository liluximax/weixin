package com.weixin.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.weixin.model.menu.Button;
import com.weixin.model.menu.ClikButton;
import com.weixin.model.menu.Menu;
import com.weixin.model.menu.ViewButton;
import com.weixin.model.token.AccessToken;


public class WeixinUtil {
//	private static final String APPID = "wxfa77a46a70803797";
//	private static final String APPSECRET = "885d0748009b94b4122bf1224f81833f";
	/**
	 * 测试账号
	 */
//	private static final String APPID = "wxefdf75446a6bedc4";
//	
//	private static final String APPSECRET = "1305e7a3a69400ac17f96bef78e2d1e6";
	
	private static final String ACCESS_TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String QUREY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String SNSAPI_USERINFO_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity =  response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject doPostStr(String url, String outStr){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = jsonObject.fromObject(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	/**
	 * 获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID",DeveloperId.APPID).replace("APPSECRET", DeveloperId.APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
		}
		return token;
	}
	
	/**
	 * 上传临时素材
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//Á¬œÓ
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//ÉèÖÃÇëÇóÍ·ÐÅÏ¢
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//ÉèÖÃ±ßœç
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//»ñµÃÊä³öÁ÷
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//Êä³ö±íÍ·
		out.write(head);

		//ÎÄŒþÕýÎÄ²¿·Ö
		//°ÑÎÄŒþÒÑÁ÷ÎÄŒþµÄ·œÊœ ÍÆÈëµœurlÖÐ
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//œáÎ²²¿·Ö
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//¶šÒå×îºóÊýŸÝ·ÖžôÏß

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//¶šÒåBufferedReaderÊäÈëÁ÷ÀŽ¶ÁÈ¡URLµÄÏìÓŠ
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
		
//		String typeName = "media_id";
//		if(!"thumb".equals(type)){
//			typeName = type + "_media_id";
//		}
//		String mediaId = jsonObj.getString(typeName);
//		return mediaId;
	}
	
	public static Menu initMenu(){
		Menu menu = new Menu();
		
		ViewButton button11 = new ViewButton();
		button11.setName("百度API");
		button11.setType("view");
		button11.setUrl("http://115.29.51.206/Weixin/baidu.jsp");
		
//		ClikButton button11 = new ClikButton();
//		button11.setName("发送地理位置");
//		button11.setType("location_select");
//		button11.setKey("11");
		
		ViewButton button21 = new ViewButton();
		button21.setName("跳转至百度");
		button21.setType("view");
		button21.setUrl("http://115.29.51.206/Weixin/NewFile.jsp");
		
		ClikButton button22 = new ClikButton();
		button22.setName("扫码");
		button22.setType("scancode_waitmsg");
		button22.setKey("22");
		
//		ClikButton button23 = new ClikButton();
//		button23.setName("微信拍照相册");
//		button23.setType("pic_weixin");
//		button23.setKey("23");
		
		ClikButton button23 = new ClikButton();
		button23.setName("支付测试");
		button23.setType("click");
		button23.setKey("23");
		
		ClikButton button24 = new ClikButton();
		button24.setName("获取主菜单");
		button24.setType("click");
		button24.setKey("24");
		
		Button button2 = new Button();
		button2.setName("事件");
		button2.setType("click");
		button2.setSub_button(new Button[]{button24, button21, button22, button23});
		
//		ClikButton button31 = new ClikButton();
//		button31.setName("点击消息推送1");
//		button31.setType("click");
//		button31.setKey("31");
//		
//		ClikButton button32 = new ClikButton();
//		button32.setName("点击消息推送2");
//		button32.setType("click");
//		button32.setKey("32");
		
		ClikButton button3 = new ClikButton();
		button3.setName("麻将计算房费");
		button3.setType("click");
		button3.setKey("3");
//		button3.setSub_button(new Button[]{button31, button32});
		
		menu.setButton(new Button[]{button3, button11, button2});
		
		return menu;
	}
	/**
	 * 带用户授权的微信菜单
	 * @return
	 */
	public static Menu initMenu2(){
		
		String url = "";
		
		Menu menu = new Menu();
		
		ViewButton button11 = new ViewButton();
		button11.setName("列表");
		button11.setType("view");
		url = SNSAPI_USERINFO_URL.replace("APPID", DeveloperId.APPID)
								 .replace("REDIRECT_URI", "http%3a%2f%2fwxoa.u-coupon.cn%2fWeixin%2fjiayou%2flist.jsp")
								 .replace("SCOPE", "snsapi_userinfo");
		System.out.println(url);
		button11.setUrl(url);
		
		ViewButton button12 = new ViewButton();
		button12.setName("地图");
		button12.setType("view");
		url = SNSAPI_USERINFO_URL.replace("APPID", DeveloperId.APPID)
								 .replace("REDIRECT_URI", "http%3a%2f%2fwxoa.u-coupon.cn%2fWeixin%2fjiayou%2fmap.jsp")
								 .replace("SCOPE", "snsapi_userinfo");
		button12.setUrl(url);
		
		Button button1 = new Button();
		button1.setName("coupon");
		button1.setType("click");
		button1.setSub_button(new Button[]{button11, button12});
		
		
		ViewButton button21 = new ViewButton();
		button21.setName("列表");
		button21.setType("view");
		url = SNSAPI_USERINFO_URL.replace("APPID", DeveloperId.APPID)
								 .replace("REDIRECT_URI", "http%3a%2f%2f115.29.51.206%2fWeixin%2fjiayou%2flist.jsp")
								 .replace("SCOPE", "snsapi_userinfo");
		System.out.println(url);
		button21.setUrl(url);
		
		ViewButton button22 = new ViewButton();
		button22.setName("地图");
		button22.setType("view");
//		url = SNSAPI_USERINFO_URL.replace("APPID", APPID)
//								 .replace("REDIRECT_URI", "http%3a%2f%2f115.29.51.206%2fWeixin%2fjiayou%2fmap.jsp")
//								 .replace("SCOPE", "snsapi_userinfo");
		button22.setUrl("http://115.29.51.206/Weixin/jiayou/map.jsp");
		
		Button button2 = new Button();
		button2.setName("ali");
		button2.setType("click");
		button2.setSub_button(new Button[]{button21, button22});
		
		
		ViewButton button3 = new ViewButton();
		button3.setName("userinfo授权");
		button3.setType("view");
		url = SNSAPI_USERINFO_URL.replace("APPID", DeveloperId.APPID)
								 .replace("REDIRECT_URI", "http%3a%2f%2f115.29.51.206%2fWeixin%2fadd.jsp")
								 .replace("SCOPE", "snsapi_userinfo");
		button3.setUrl(url);
		
		menu.setButton(new Button[]{button1, button2, button3});
		
		return menu;
	}
	
	/*
	 * u-coupon菜单
	 */
	public static Menu initMenu3(){
		
		String url = "";
		
		Menu menu = new Menu();
		
		ViewButton button11 = new ViewButton();
		button11.setName("列表");
		button11.setType("view");
		url = SNSAPI_USERINFO_URL.replace("APPID", DeveloperId.APPID)
								 .replace("REDIRECT_URI", "http%3a%2f%2fwxoa.u-coupon.cn%2fWeixin%2fjiayou%2flist.jsp")
								 .replace("SCOPE", "snsapi_userinfo");
		System.out.println(url);
		button11.setUrl(url);
		
		ViewButton button12 = new ViewButton();
		button12.setName("地图");
		button12.setType("view");
		url = SNSAPI_USERINFO_URL.replace("APPID", DeveloperId.APPID)
								 .replace("REDIRECT_URI", "http%3a%2f%2fwxoa.u-coupon.cn%2fWeixin%2fjiayou%2fmap.jsp")
								 .replace("SCOPE", "snsapi_userinfo");
		button12.setUrl(url);
		
		Button button1 = new Button();
		button1.setName("我要加油");
		button1.setType("click");
		button1.setSub_button(new Button[]{button11, button12});
		
		ViewButton button21 = new ViewButton();
		button21.setName("公司简介");
		button21.setType("view");
		url = "http://www.u-coupon.cn/mobile/jianjie.html";
		button21.setUrl(url);
		
		ViewButton button22 = new ViewButton();
		button22.setName("使用帮助");
		button22.setType("view");
		url = "http://www.u-coupon.cn/mobile/help.html";
		button22.setUrl(url);
		
		Button button2 = new Button();
		button2.setName("业务介绍");
		button2.setType("click");
		button2.setSub_button(new Button[]{button21, button22});
		
		ViewButton button3 = new ViewButton();
		button3.setName("获取App");
		button3.setType("view");
		button3.setUrl("http://www.u-coupon.cn/mobile/download.html");
		menu.setButton(new Button[]{button1, button2, button3});
		
		return menu;
	}
	
	public static int creatMenu(String token, String menu){
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	public static String queryMenu(String token){
		String url = QUREY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return JSONObject.fromObject(jsonObject).toString();
	}
}
