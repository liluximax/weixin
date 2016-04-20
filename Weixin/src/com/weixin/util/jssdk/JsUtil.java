package com.weixin.util.jssdk;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;
import com.weixin.model.token.AccessToken;
import com.weixin.model.token.JsApiTicket;
import com.weixin.model.token.JsSignature;
import com.weixin.util.DeveloperId;
import com.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

public class JsUtil {
	
//	private static final String APPID = "wxefdf75446a6bedc4";
//	
//	private static final String APPSECRET = "1305e7a3a69400ac17f96bef78e2d1e6";
	
	private static final String ACCESS_TOKEN_URL ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", DeveloperId.APPID).replace("APPSECRET", DeveloperId.APPSECRET);
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
		}
		return token;
	} 
	
	public static JsApiTicket getJsApiTicket(String access_token){
		JsApiTicket ticket = new JsApiTicket();
		String url = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		if(jsonObject != null){
			ticket.setErrcode(jsonObject.getInt("errcode"));
			ticket.setErrmsg(jsonObject.getString("errmsg"));
			ticket.setTicket(jsonObject.getString("ticket"));
			ticket.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return ticket;
	}
	
    public static JsSignature getJsSignature(String jsapi_ticket, String url) {
        JsSignature ret = new JsSignature();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.setUrl(url);
        ret.setJsapi_ticket(jsapi_ticket);
        ret.setNoncestr(nonce_str);
        ret.setTimestamp(timestamp);
        ret.setSignature(signature);
        
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	private static String create_nonce_str(){
		return UUID.randomUUID().toString();
	}
	
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
}
