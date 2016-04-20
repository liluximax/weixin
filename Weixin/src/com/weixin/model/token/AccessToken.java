package com.weixin.model.token;

public class AccessToken {
	
	private String token;
	
	private long expire_time;
	
	private long create_time;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(long expire_time) {
		this.expire_time = expire_time;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	
}
