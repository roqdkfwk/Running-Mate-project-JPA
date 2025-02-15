package com.suseok.run.model.service;

import com.suseok.run.model.dto.User;

import java.util.List;

public interface UserService {

	User loginUser(User user);

	List<User> search(String con);

	void signup(User user);

	void checkId(String userId);

	User selectById(String userId);
	
	User selectByNick(String userNick);

	boolean update(User user);

	boolean addRival(String userId, String rivalId);

	boolean delete(String userId);

	String findId(String name, String phoneOrEmail);

	User findPwd(String name, String phoneOrEmail, String id);

	String sendNewPassword(User foundUser);

	List<User> selectAll();

	Integer findByEmail(String email);
}
