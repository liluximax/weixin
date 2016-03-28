package com.weixin.model.token;

public class AccessToken {
	private String token;
	private int expiresIn;
	private Long currentTime;
	
	public Long getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
