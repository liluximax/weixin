package com.weixin.model.token;

public class JsApiTicket {

	private String ticket;
	
	private long expire_time;
	
	private long create_time;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
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
