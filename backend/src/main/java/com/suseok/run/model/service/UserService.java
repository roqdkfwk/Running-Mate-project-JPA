package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateUserReq;
import com.suseok.run.model.entity.Request.UpdateUserReq;
import com.suseok.run.model.entity.Response.UpdateUserRes;
import com.suseok.run.model.entity.User;

import java.util.List;

public interface UserService {

	User loginUser(User user);

	List<User> search(String con);

	void signup(CreateUserReq createUserReq);

	void checkId(String userId);

	void checkNickname(String userNick);

	void delete(String userId);

	UpdateUserRes update(Long userSeq, UpdateUserReq updateUserReq);

	User selectByNick(String userNick);

	String findId(String name, String phoneOrEmail);

	User findPw(String name, String phoneOrEmail, String id);

	String sendNewPassword(User foundUser);

	List<User> selectAll();

	Integer findByEmail(String email);
}
