package com.suseok.run.model.service;

import com.suseok.run.common.ConflictException;
import com.suseok.run.common.NotFoundException;
import com.suseok.run.model.dao.UserDao;
import com.suseok.run.model.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public User loginUser(User user) {
		return userDao.loginUser(user);
	}

	@Transactional
	@Override
	public void signup(User user) {
		if (!userDao.signup(user)) {
			throw new IllegalStateException("회원가입 중 예상치 못한 오류가 발생했습니다.");
		}

		redisTemplate.delete(user.getUserId());
		redisTemplate.delete(user.getUserNick());
		redisTemplate.delete(user.getEmail());
	}

	@Override
	public void checkId(String userId) {
		if (redisTemplate.hasKey(userId) || userDao.selectById(userId) != null) {
			throw new ConflictException("이미 사용 중인 아이디입니다.");
		}

		redisTemplate.opsForValue().set(userId, "true", Duration.ofMinutes(10));
	}

	@Override
	public User selectById(String userId) {
		return userDao.selectById(userId);
	}

	@Override
	public boolean update(User user) {
		return userDao.update(user);
	}

	@Override
	public boolean addRival(String userId, String rivalId) {
		int userSeq = userDao.selectById(userId).getUserSeq();
		int rivalSeq = userDao.selectById(rivalId).getUserSeq();
		return userDao.addRival(userSeq, rivalSeq);
	}

	@Override
	public List<User> search(String con) {
		return userDao.search(con);
	}

	@Override
	public void delete(String userId) {
		if (!userDao.delete(userId)) {
			throw new IllegalStateException("회원탈퇴 중 예기치 못한 오류가 발생했습니다.");
		}
	}

	@Override
	public String findId(String name, String phoneOrEmail) {
		String userId = userDao.findId(name, phoneOrEmail);
		if (userId == null) {
			throw new NotFoundException("찾을 수 없는 회원입니다.");
		}
		return userId;
	}

	@Override
	public User findPwd(String name, String phoneOrEmail, String id) {
		return userDao.findPwd(name, phoneOrEmail, id);
	}

	@Override
	public String sendNewPassword(User user) {

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
		
		return randomString;
	}

	@Override
	public User selectByNick(String userNick) {
		if (redisTemplate.hasKey(userNick) || userDao.selectByNick(userNick) != null) {
			throw new ConflictException("이미 사용 중인 닉네임입니다.");
		}

		redisTemplate.opsForValue().set(userNick, "true", Duration.ofMinutes(10));
		return userDao.selectByNick(userNick);
	}

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}

	@Override
	public Integer findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
