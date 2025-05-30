package com.suseok.run.controller;

import com.suseok.run.common.exception.RequiredAuth;
import com.suseok.run.model.entity.Request.CreateUserReq;
import com.suseok.run.model.entity.Request.UpdateUserReq;
import com.suseok.run.model.entity.Response.UpdateUserRes;
import com.suseok.run.model.entity.Response.UpdateUserResult;
import com.suseok.run.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
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
			@ApiResponse(responseCode = "200", description = "사용할 수 있는 아이디입니다."),
			@ApiResponse(responseCode = "400", description = "잘못된 아이디 형식입니다."),
			@ApiResponse(responseCode = "409", description = "이미 사용 중인 아이디입니다.")
	})
	public ResponseEntity<Void> checkId(
			@RequestParam String userId
	) {
		log.info("userId : " + userId);
		userService.checkId(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/check-nickname")
	@Operation(summary = "닉네임 중복 확인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "사용할 수 있는 닉네임입니다."),
			@ApiResponse(responseCode = "400", description = "잘못된 닉네임 형식입니다."),
			@ApiResponse(responseCode = "409", description = "이미 사용 중인 닉네임입니다.")
	})
	public ResponseEntity<Void> checkNick(
			@RequestParam String nickname
	) {
		userService.checkNickname(nickname);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@SecurityRequirement(name = "JWT")
	@SecurityRequirement(name = "basicAuth")
	@DeleteMapping
	@Operation(summary = "회원탈퇴")
	@RequiredAuth
	public ResponseEntity<Void> withdraw(Authentication authentication) {
		userService.delete(Long.valueOf(authentication.getName()));
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PatchMapping
	@Operation(summary = "회원정보수정")
	@RequiredAuth
	public ResponseEntity<UpdateUserRes> updateMyPage(
			Authentication authentication,
			@RequestBody UpdateUserReq updateUserReq
	) {
		log.info("authentication: {}", authentication.toString());
		System.out.println("authenticationSYS: " + authentication.getName());
		Long userSeq = Long.valueOf(authentication.getName());
		UpdateUserResult updateUserResult = userService.update(userSeq, updateUserReq);

		return ResponseEntity.status(HttpStatus.OK)
				.header("Authorization", updateUserResult.getAcccessToken())
				.body(updateUserResult.getUpdateUserRes());
	}
}
