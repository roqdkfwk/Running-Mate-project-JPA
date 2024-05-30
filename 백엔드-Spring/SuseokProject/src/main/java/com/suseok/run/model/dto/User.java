package com.suseok.run.model.dto;

import java.sql.Timestamp;

public class User {

	private int userSeq;
	private String userId;
	private String userPwd;
	private String userName;
	private String userNick;
	private String email;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String address;
	private String img;
	private String phone;
	private boolean exposure;

	public User() {
	}


	public User(int userSeq, String userId, String userPwd, String userName, String userNick, String email,
			Timestamp createdAt, Timestamp updatedAt, String address, String img, String phone, boolean exposure) {
		super();
		this.userSeq = userSeq;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userNick = userNick;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.address = address;
		this.img = img;
		this.phone = phone;
		this.exposure = exposure;
	}


	public boolean isExposure() {
		return exposure;
	}


	public void setExposure(boolean exposure) {
		this.exposure = exposure;
	}


	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
