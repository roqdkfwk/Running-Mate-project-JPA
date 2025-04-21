package com.suseok.run.model.service;

import com.suseok.run.model.entity.Request.CreateUserReq;
import com.suseok.run.model.entity.Request.UpdateUserReq;
import com.suseok.run.model.entity.Response.UpdateUserResult;
import com.suseok.run.model.entity.User;

public interface UserService {

	void signup(CreateUserReq createUserReq);

	void checkId(String userId);

	void checkNickname(String userNick);

	void delete(Long userSeq);

	UpdateUserResult update(Long userSeq, UpdateUserReq updateUserReq);

	User selectByNick(String userNick);

	String sendNewPassword(User foundUser);
}
