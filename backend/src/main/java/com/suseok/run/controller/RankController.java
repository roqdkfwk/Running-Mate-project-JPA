package com.suseok.run.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.dto.UserRankRecord;
import com.suseok.run.model.service.RankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping
@Tag(name = "RankRecordRestController", description = "랭킹R")
public class RankController {

	// TODO Controller와 Service 로직 분리
	private final RankService rs;

	public RankController(RankService rs) {
		this.rs = rs;
	}

	@PostMapping("/records")
	@Operation(summary = "UserRankRecord갱신")
	public ResponseEntity<?> saveRecords(@RequestBody UserRankRecord record, @RequestHeader("userId") String userId) {
		rs.insertRankRecord(record, userId);
		System.out.println("userRankRecord가 갱신되었습니다");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@AuthRequired
	@GetMapping("/myRR")
	@Operation(summary = "myRR", description = "(기본) 뱃지클릭, 스트릭클릭, 프사클릭 등등")
	public ResponseEntity<UserRankRecord> myRR(@RequestHeader("userId") String userId) {

		UserRankRecord userRankRecord = rs.selectByUser(userId);

		if (userRankRecord == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<UserRankRecord>(userRankRecord, HttpStatus.OK);
	}

	@AuthRequired
	@GetMapping("/rank/user/{rivalId}")
	@Operation(summary = "compareRankRecord", description = "")
	public ResponseEntity<UserRankRecord> compareRankRecord(@PathVariable("rivalId") String rivalId,
			@RequestHeader("userId") String userId) {

		UserRankRecord userRankRecord = rs.selectByUser(rivalId);
		System.out.println("라이벌 레코드" + userRankRecord);
		if (userRankRecord != null)
			return new ResponseEntity<UserRankRecord>(userRankRecord, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/rank/user")
	@Operation(summary = "totalUserRank", description = "condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<?> totalUserRank(@RequestParam String con, @RequestHeader("userId") String userId) {
		System.out.println("유저랭크 요청");
		System.out.println(userId);
		if (userId != null) {
			List<UserRankRecord> userRecords = rs.selectAllOrderBy(con, userId);
			System.out.println(userRecords);
			if (userRecords != null)
				return new ResponseEntity<List<UserRankRecord>>(userRecords, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			List<UserRankRecord> userRecords = rs.selectAllOrderBy(con);
			System.out.println(userRecords);
			if (userRecords != null)
				return new ResponseEntity<List<UserRankRecord>>(userRecords, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/rank/group")
	@Operation(summary = "totalGroupRank", description = "condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<?> totalGroupRank(@RequestParam String con) {

		List<Group> groups = rs.selectGroupsOrderBy(con);

		if (groups != null)
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@AuthRequired
	@GetMapping("/rank/group/my")
	@Operation(summary = "myGroupRank", description = "condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<?> myGroupRank(@RequestParam String con, @RequestHeader("userId") String userId) {

		List<Group> groups = rs.selectMyGroupsOrderBy(con, userId);

		if (groups != null)
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/rank/group/{groupId}")
	@Operation(summary = "GroupMemberank", description = "groupid로 그룹 구분, condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<List<UserRankRecord>> GroupMemberank(@RequestParam String con,
			@PathVariable("groupId") int groupId) {
		List<UserRankRecord> userRecords = rs.selectAllMemberOrderBy(con, groupId);

		if (userRecords != null) {
			System.out.println(userRecords);
			return new ResponseEntity<List<UserRankRecord>>(userRecords, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
