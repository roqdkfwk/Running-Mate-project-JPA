package com.suseok.run.controller;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.UserRankRecord;
import com.suseok.run.model.service.RankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "RankRecordRestController", description = "랭킹R")
@RequiredArgsConstructor
public class RankController {

	// TODO Controller와 Service 로직 분리
	private final RankService rankService;

	@AuthRequired
	@GetMapping("/myRR")
	@Operation(summary = "myRR", description = "(기본) 뱃지클릭, 스트릭클릭, 프사클릭 등등")
	public ResponseEntity<UserRankRecord> myRR(
			@RequestHeader("userId") String userId
	) {
		UserRankRecord userRankRecord = rankService.selectByUser(userId);
		if (userRankRecord == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<UserRankRecord>(userRankRecord, HttpStatus.OK);
	}

	@AuthRequired
	@GetMapping("/rank/user/{rivalId}")
	@Operation(summary = "compareRankRecord", description = "")
	public ResponseEntity<UserRankRecord> compareRankRecord(
			@PathVariable("rivalId") String rivalId,
			@RequestHeader("userId") String userId
	) {
		UserRankRecord userRankRecord = rankService.selectByUser(rivalId);
		if (userRankRecord != null)
			return new ResponseEntity<UserRankRecord>(userRankRecord, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/rank/user")
	@Operation(summary = "totalUserRank", description = "condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<?> totalUserRank(
			@RequestParam String con,
			@RequestHeader("userId") String userId
	) {
		if (userId != null) {
			List<UserRankRecord> userRecords = rankService.selectAllOrderBy(con, userId);
			if (userRecords != null)
				return new ResponseEntity<List<UserRankRecord>>(userRecords, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			List<UserRankRecord> userRecords = rankService.selectAllOrderBy(con);
			if (userRecords != null)
				return new ResponseEntity<List<UserRankRecord>>(userRecords, HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/rank/group")
	@Operation(summary = "totalGroupRank", description = "condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<?> totalGroupRank(
			@RequestParam String con
	) {
		List<Group> groups = rankService.selectGroupsOrderBy(con);
		if (groups != null)
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@AuthRequired
	@GetMapping("/rank/group/my")
	@Operation(summary = "myGroupRank", description = "condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<?> myGroupRank(
			@RequestParam String con,
			@RequestHeader("userId") String userId
	) {
		List<Group> groups = rankService.selectMyGroupsOrderBy(con, userId);
		if (groups != null)
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/rank/group/{groupId}")
	@Operation(summary = "GroupMemberank", description = "groupid로 그룹 구분, condition 으로 orderBy(pace, frequency, distance)구분")
	public ResponseEntity<List<UserRankRecord>> GroupMemberank(
			@RequestParam String con,
			@PathVariable("groupId") int groupId
	) {
		List<UserRankRecord> userRecords = rankService.selectAllMemberOrderBy(con, groupId);
		if (userRecords != null) {
			return new ResponseEntity<List<UserRankRecord>>(userRecords, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
