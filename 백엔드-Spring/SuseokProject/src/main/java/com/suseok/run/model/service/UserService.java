package com.suseok.run.model.service;

import java.util.List;

import com.suseok.run.model.dto.User;


public interface UserService {

	// 로그인한 User의 정보
	User loginUser(User user);
	
	// User 리스트를 
	List<User> search(String con);

	// 전체 유저 리스트
	List<User> selectAll();
	
	// 
	boolean insert(User user);
	
	// userId로 유저 검색
	User selectById(String userId);
	
	// userNick으로 유저 검색
	User selectByNick(String userNick);
	
	boolean update(User user);
	
	// 라이벌 등록
	boolean addRival(String userId, String rivalId);
	
	boolean delete(String userId);
	
	// 아이디 찾기
	User findId(String name, String phoneOrEmail);
	
	// 비밀번호 찾기
	User findPwd(String name, String phoneOrEmail, String id);
	
	String sendNewPassword(User foundUser);
	
}
