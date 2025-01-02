package com.suseok.run.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRankRecord {
	private int userSeq; // 유저 id
	private String userName;
	private String userNick;
	private String userId;
	private boolean myRival;
	private double frequency; // 빈도
	private double totalDistance; // 유저가 달린 누적 거리
	private double highestPace; // 최고 속력

	// 기본 생성자 삭제

	public UserRankRecord(int userSeq, double frequency, double totalDistance, double highestPace) {
		this.userSeq = userSeq;
		this.frequency = frequency;
		this.totalDistance = totalDistance;
		this.highestPace = highestPace;
	}

	public boolean isMyRival() {
		return myRival;
	}

	// getter, setter 삭제

	@Override
	public String toString() {
		return "UserRankRecord [userSeq=" + userSeq + ", userName=" + userName + ", userNick=" + userNick + ", userId="
				+ userId + ", isMyRival=" + myRival + ", frequency=" + frequency + ", totalDistance=" + totalDistance
				+ ", highestPace=" + highestPace + "]";
	}
}
