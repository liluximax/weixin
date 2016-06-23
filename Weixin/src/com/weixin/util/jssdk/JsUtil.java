package com.weixin.util.jssdk;

import com.spring.service.cache.JsApiTicketCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.service.AccessTokenService;
import com.spring.service.JsApiTicketService;
import com.weixin.model.token.JsSignature;
import com.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import java.util.UUID;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

@Service("jsutil")
public class JsUtil {
	
//	@Autowired
//	private JsApiTicketService jsApiTicketService;

    @Autowired
    private JsApiTicketCache jsApiTicketCache;
	
    public JsSignature sign(String url) {
    	
    	JsSignature ret = new JsSignature();
    	
    	String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";
//        String jsapi_ticket = jsApiTicketService.getJsApiTicket();

        //换成下面使用缓存的方式
        String jsapi_ticket = jsApiTicketCache.getJsApiTicketByCache();

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
//        System.out.println(string1);

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

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
