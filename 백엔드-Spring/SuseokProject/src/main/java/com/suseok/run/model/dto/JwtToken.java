package com.suseok.run.model.dto;


public class JwtToken {
	private int userSeq;
	private String refreshToken;
	
	public JwtToken () {}
	

	public JwtToken(int userSeq, String refreshToken) {
		this.userSeq = userSeq;
		this.refreshToken = refreshToken;
	}
	
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	

	

}
