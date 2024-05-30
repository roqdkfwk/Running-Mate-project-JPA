package com.suseok.run.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.jwtutill.JwtUtil;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.service.AuthService;
import com.suseok.run.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "UserRestController", description = "유저CRUD")
public class UserController {

	private final UserService us;
	
	private final String SUCCESS ="SUCCESS";
	private final String FAIL ="FAIL";
	
	public UserController(UserService us) {
		this.us = us;
	}

	@PostMapping("/signup")
	@Operation(summary = "signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		if (us.insert(user)) 
			
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		else
			return new ResponseEntity<String>(FAIL ,HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping
	@Operation(summary = "selectAllUsers")
	public ResponseEntity<List<User>> selectAllUsers() {
		return new ResponseEntity<List<User>>(us.selectAll(),HttpStatus.OK);
	}

	@GetMapping("/signup/ci/{checkId}")
	@Operation(summary = "checkId")
	public ResponseEntity<?> checkId(@PathVariable String checkId) {
		if (us.selectById(checkId) != null) {
			System.out.println("이미 있는 아이디임");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
		else
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/signup/cn/{checkNick}")
	@Operation(summary = "checkNick")
	public ResponseEntity<?> checkNick(@PathVariable String checkNick) {
		if (us.selectByNick(checkNick) != null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/findId")
    @Operation(summary = "findId")
    public ResponseEntity<?> findId(@RequestBody User user) {
        User foundUser = us.findId(user.getUserName(), user.getPhone());
        if (foundUser == null) {
            foundUser = us.findId(user.getUserName(), user.getEmail());
        }

        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(foundUser.getUserId(), HttpStatus.OK);
    }

    @PostMapping("/findPwd")
    @Operation(summary = "findPwd")
    public ResponseEntity<?> findPwd(@RequestBody User user) {
        User foundUser = us.findPwd(user.getUserName(), user.getPhone(), user.getUserId());
        if (foundUser == null) {
            foundUser = us.findPwd(user.getUserName(), user.getEmail(), user.getUserId());
        }

        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 새로운 비밀번호 생성 
        us.sendNewPassword(foundUser);
        //(보냈다 치고 ^^)
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@AuthRequired
	@DeleteMapping("/withdraw")
	@Operation(summary = "withdraw")
	public ResponseEntity<?> withdraw(@RequestHeader("userId") String userId) {
		if(us.delete(userId))
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@AuthRequired 
	@GetMapping("/myPage")

	@Operation(summary = "myPage", description = "유저 정보")
	public ResponseEntity<User> myPage(@RequestHeader("userId") String userId) {

		User user = us.selectById(userId);

		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@AuthRequired 
	@PutMapping
	@Operation(summary = "updateMyPage")
	public ResponseEntity<?> updateMyPage(@RequestHeader("userId") String userId, @RequestBody User user) {
		if (userId != user.getUserId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (us.update(user))
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@AuthRequired 
	@GetMapping("/add/{rivalId}")
	@Operation(summary = "addRival")
	public ResponseEntity<?> addRival(@RequestHeader("userId") String userId, @PathVariable("rivalId") String rivalId) {
		System.out.println("여기에 오긴함");
		if (us.addRival(userId, rivalId))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/search")
	@Operation(summary = "searchUser")
	public ResponseEntity<List<User>> searchUser(@RequestParam String con) {
		List<User> users = us.search(con);
		if (users != null)
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
