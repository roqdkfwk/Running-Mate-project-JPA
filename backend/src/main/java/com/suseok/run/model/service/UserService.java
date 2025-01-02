package com.suseok.run.model.service;

import java.util.List;

import com.suseok.run.model.dto.User;

public interface UserService {

	User loginUser(User user);

	List<User> search(String con);

	boolean insert(User user);

	User selectById(String userId);
	
	User selectByNick(String userNick);

	boolean update(User user);

	boolean addRival(String userId, String rivalId);

	boolean delete(String userId);

	User findId(String name, String phoneOrEmail);

	User findPwd(String name, String phoneOrEmail, String id);

	String sendNewPassword(User foundUser);

	List<User> selectAll();
}
