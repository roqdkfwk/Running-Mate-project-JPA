package com.suseok.run.model.service;

import com.suseok.run.common.exception.ConflictException;
import com.suseok.run.common.exception.NotFoundException;
import com.suseok.run.model.dao.UserDao;
import com.suseok.run.model.entity.Request.CreateUserReq;
import com.suseok.run.model.entity.Request.UpdateUserReq;
import com.suseok.run.model.entity.Response.UpdateUserRes;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.repository.UserRepository;
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

	private final UserRepository userRepository;
	private final UserDao userDao;
	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public User loginUser(User user) {
		return userDao.loginUser(user);
	}

	@Transactional
	@Override
	public void signup(CreateUserReq createUserReq) {
		// 1. 중복확인
		if (userRepository.findByUserId(createUserReq.getUserId()).isPresent()) {
			throw new ConflictException("이미 존재하는 회원입니다.");
		}

		// 2. 회원생성
		User user = createUserReq.toEntity();
		userRepository.save(user);

		redisTemplate.delete(user.getUserId());
		redisTemplate.delete(user.getUserNick());
		redisTemplate.delete(user.getEmail());
	}

	@Override
	public void checkId(String userId) {
		if (redisTemplate.hasKey(userId) || userRepository.findByUserId(userId).isPresent()) {
			throw new ConflictException("이미 사용 중인 아이디입니다.");
		}

		redisTemplate.opsForValue().set(userId, "true", Duration.ofMinutes(10));
	}

	@Override
	public void checkNickname(String userNick) {
		if (redisTemplate.hasKey(userNick) || userRepository.findByNick(userNick).isPresent()) {
			throw new ConflictException("이미 사용 중인 닉네임입니다.");
		}

		redisTemplate.opsForValue().set(userNick, "true", Duration.ofMinutes(10));
	}

	@Override
	public UpdateUserRes update(
			Long userSeq,
			UpdateUserReq updateUserReq
	) {
		User user = userRepository.findById(userSeq).orElseThrow(
				() -> new NotFoundException("존재하지 않는 사용자입니다.")
		);

		updateUserReq.toEntity(user);
		return UpdateUserRes.fromEntity(user);
	}

	@Override
	public List<User> search(String con) {
		return userDao.search(con);
	}

	@Override
	public void delete(String userId) {
		User user = userRepository.findById(1L).orElseThrow(
				() -> new IllegalStateException("회원탈퇴 중 예기치 못한 오류가 발생했습니다.")
		);

		userRepository.delete(user);
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
	public User findPw(String name, String phoneOrEmail, String id) {
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
		user.setUserPw(randomString);
//		update(user);
		
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
