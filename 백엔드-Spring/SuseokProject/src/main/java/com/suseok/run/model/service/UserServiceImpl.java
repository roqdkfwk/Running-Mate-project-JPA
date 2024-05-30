package com.suseok.run.model.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suseok.run.model.dao.UserDao;
import com.suseok.run.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao ud;

	@Override
	public User loginUser(User user) {
		return ud.loginUser(user);
	}

	@Override
	public boolean insert(User user) {
		return ud.insert(user);
	}

	@Override
	public User selectById(String userId) {
		return ud.selectById(userId);
	}

	@Override
	public boolean update(User user) {
		return ud.update(user);
	}

	@Override
	public boolean addRival(String userId, String rivalId) {
		int userSeq = ud.selectById(userId).getUserSeq();
		int rivalSeq = ud.selectById(rivalId).getUserSeq();
		return ud.addRival(userSeq, rivalSeq);
	}

	@Override
	public List<User> search(String con) {
		return ud.search(con);
	}

	@Override
	public boolean delete(String userId) {
		return ud.delete(userId);
	}

	@Override
	public User findId(String name, String phoneOrEmail) {
		return ud.findId(name, phoneOrEmail);
	}

	@Override
	public User findPwd(String name, String phoneOrEmail, String id) {
		return ud.findPwd(name, phoneOrEmail, id);
	}

	@Override
	public void sendNewPassword(User user) {

		// 사용할 문자와 숫자 집합
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		Random random = new Random();
		StringBuilder sb = new StringBuilder(10);

		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(index));
		}

		String randomString = sb.toString();
		user.setUserPwd(randomString);
		update(user);
		
		//유저 폰이나 메일로 발송하는 api찾기
		

	}

	@Override
	public User selectByNick(String userNick) {
		return ud.selectByNick(userNick);
	}

	@Override
	public List<User> selectAll() {
		return ud.selectAll();
	}

}
