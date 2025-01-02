package com.suseok.run.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	// 기본 생성자, 모든 필드 갖는 생성자 삭제
	
	// getter, setter 삭제 

}
