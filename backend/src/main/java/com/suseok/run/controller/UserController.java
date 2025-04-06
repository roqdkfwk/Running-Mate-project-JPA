package com.suseok.run.controller;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.entity.Request.CreateUserReq;
import com.suseok.run.model.entity.Request.FindIdReq;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "UserRestController", description = "유저 CRUD")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	@Operation(summary = "회원가입")
	public ResponseEntity<Void> signup(
			@RequestBody CreateUserReq createUserReq
	) {
		userService.signup(createUserReq);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/check-id")
	@Operation(summary = "아이디 중복 확인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "잘못된 아이디 형식입니다."),
			@ApiResponse(responseCode = "409", description = "이미 사용 중인 아이디입니다.")
	})
	public ResponseEntity<Void> checkId(
			@RequestParam String userId
	) {
		userService.checkId(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/check-nickname")
	@Operation(summary = "닉네임 중복 확인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "잘못된 닉네임 형식입니다."),
			@ApiResponse(responseCode = "409", description = "이미 사용 중인 닉네임입니다.")
	})
	public ResponseEntity<?> checkNick(
			@RequestParam String nickname
	) {
		userService.selectByNick(nickname);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	@Operation(summary = "selectAllUsers")
	public ResponseEntity<List<User>> selectAllUsers() {
		return new ResponseEntity<List<User>>(userService.selectAll(),HttpStatus.OK);
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
		userService.delete(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
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
