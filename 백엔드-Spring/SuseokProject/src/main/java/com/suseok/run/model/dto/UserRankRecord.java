package com.suseok.run.model.dto;

public class UserRankRecord {
	private int userSeq; // 유저 id
	private String userName;
	private String userNick;
	private String userId;
	private boolean myRival;
	private double frequency; // 빈도
	private double totalDistance; // 유저가 달린 누적 거리
	private double highestPace; // 최고 속력

	public UserRankRecord() {
	}

	
	public UserRankRecord(int userSeq, double frequency, double totalDistance, double highestPace) {
		this.userSeq = userSeq;
		this.frequency = frequency;
		this.totalDistance = totalDistance;
		this.highestPace = highestPace;
	}
	


	public boolean isMyRival() {
		return myRival;
	}


	public void setMyRival(boolean myRival) {
		this.myRival = myRival;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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



	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public double getHighestPace() {
		return highestPace;
	}

	public void setHighestPace(double highestPace) {
		this.highestPace = highestPace;
	}


	@Override
	public String toString() {
		return "UserRankRecord [userSeq=" + userSeq + ", userName=" + userName + ", userNick=" + userNick + ", userId="
				+ userId + ", isMyRival=" + myRival + ", frequency=" + frequency + ", totalDistance=" + totalDistance
				+ ", highestPace=" + highestPace + "]";
	}



}
