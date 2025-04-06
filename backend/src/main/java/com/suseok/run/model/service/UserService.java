package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateUserReq;
import com.suseok.run.model.entity.User;

import java.util.List;

public interface UserService {

	User loginUser(User user);

	List<User> search(String con);

	void signup(CreateUserReq createUserReq);

	void checkId(String userId);

	void delete(String userId);

	User selectByNick(String userNick);

	boolean update(User user);

	String findId(String name, String phoneOrEmail);

	User findPwd(String name, String phoneOrEmail, String id);

	String sendNewPassword(User foundUser);

	List<User> selectAll();

	Integer findByEmail(String email);
}
