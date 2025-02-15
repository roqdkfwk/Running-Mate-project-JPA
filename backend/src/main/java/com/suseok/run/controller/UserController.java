package com.suseok.run.controller;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.dto.Request.FindIdReq;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "UserRestController", description = "유저CRUD")
@RequiredArgsConstructor
public class UserController {

	// TODO Controller와 Service 로직 분리
	private final UserService userService;

	@PostMapping("/signup")
	@Operation(summary = "회원가입")
	public ResponseEntity<Void> signup(
			@RequestBody User user
	) {
		userService.signup(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/users/{userId}/exists")
	@Operation(summary = "아이디 중복 확인")
	public ResponseEntity<Void> checkId(
			@PathVariable String userId
	) {
		userService.checkId(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping
	@Operation(summary = "selectAllUsers")
	public ResponseEntity<List<User>> selectAllUsers() {
		return new ResponseEntity<List<User>>(userService.selectAll(),HttpStatus.OK);
	}

	@GetMapping("/users/{nickname}/exists")
	@Operation(summary = "닉네임 중복 확인")
	public ResponseEntity<?> checkNick(
			@PathVariable String nickname
	) {
		userService.selectByNick(nickname);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/findId")
    @Operation(summary = "아이디 찾기")
    public ResponseEntity<?> findId(
			@RequestBody FindIdReq findIdReq
	) {
		String userId = userService.findId(findIdReq.getUserName(), findIdReq.getPhoneOrEmail());
		return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @PostMapping("/findPwd")
    @Operation(summary = "비밀번호 찾기")
    public ResponseEntity<?> findPwd(
			@RequestBody User user
	) {
        User foundUser = userService.findPwd(user.getUserName(), user.getPhone(), user.getUserId());
        if (foundUser == null) {
            foundUser = userService.findPwd(user.getUserName(), user.getEmail(), user.getUserId());
        }

        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 새로운 비밀번호 생성 
        String newPwd = userService.sendNewPassword(foundUser);
        return new ResponseEntity<>(newPwd, HttpStatus.OK);
    }
	
	@AuthRequired
	@DeleteMapping("/withdraw")
	@Operation(summary = "회원탈퇴")
	public ResponseEntity<?> withdraw(
			@RequestHeader("userId") String userId
	) {
		if(userService.delete(userId))
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@AuthRequired 
	@GetMapping("/myPage")
	@Operation(summary = "마이페이지", description = "유저 정보")
	public ResponseEntity<User> myPage(
			@RequestHeader("userId") String userId
	) {
		User user = userService.selectById(userId);
		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@AuthRequired 
	@PutMapping
	@Operation(summary = "updateMyPage")
	public ResponseEntity<?> updateMyPage(
			@RequestHeader("userId") String userId,
			@RequestBody User user
	) {
		if (userId != user.getUserId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (userService.update(user))
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@AuthRequired 
	@GetMapping("/add/{rivalId}")
	@Operation(summary = "라이벌 추가")
	public ResponseEntity<?> addRival(
			@RequestHeader("userId") String userId,
			@PathVariable("rivalId") String rivalId
	) {
		if (userService.addRival(userId, rivalId))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/search")
	@Operation(summary = "searchUser")
	public ResponseEntity<List<User>> searchUser(
			@RequestParam String con
	) {
		List<User> users = userService.search(con);
		if (users != null)
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
