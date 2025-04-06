package com.suseok.run.model.dao;

import com.suseok.run.model.entity.User;

import java.util.List;

public interface UserDao {
	// CRUD : insert, selectAll, update, delete
	boolean signup(User user);

	List<User> selectAll();

	User selectById(String userId);
	
	User selectBySeq(int userSeq);

	User selectByNick(String userNick);

	boolean update(User user);

	boolean delete(String userId);

	List<User> search(String con);

	boolean addRival(int userSeq, int rivalSeq); 

	String findId(String name, String phoneOrEmail);

	User findPwd(String name, String phoneOrEmail, String id);

	User loginUser(User user);

	Integer findByEmail(String email);
}
