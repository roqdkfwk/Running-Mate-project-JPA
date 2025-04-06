package com.suseok.run.controller;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@Tag(name = "GroupRestController", description = "그룹CRUD")
@RequiredArgsConstructor
public class GroupController {

	// TODO Controller와 Service 로직 분리
	private final GroupService groupService;

	@AuthRequired
	@PostMapping
	@Operation(summary = "createGroup")
	public ResponseEntity<Group> createGroup(
			@RequestBody Group group,
			@RequestHeader("userId") String userId
	) {
		if (groupService.insert(group,userId))
			return new ResponseEntity<>(group, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{groupId}")
	@Operation(summary = "selectGroupById")
	public ResponseEntity<Group> selectGroupById(
			@PathVariable("groupId") int groupId
	) {
		return new ResponseEntity<>(groupService.selectById(groupId),HttpStatus.OK);
	}
	
	@GetMapping
	@Operation(summary = "groupList")
	public ResponseEntity<List<Group>> groupList() {
		return new ResponseEntity<List<Group>>(groupService.selectAll(),HttpStatus.OK);
	}

//	@AuthRequired
	@GetMapping("/join/{groupId}")
	@Operation(summary = "joinGroup")
	public ResponseEntity<String> joinGroup(
			@PathVariable("groupId") int groupId,
			@RequestHeader("userId") String userId
	) {
		groupService.join(groupId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@AuthRequired
	@DeleteMapping("/exit/{groupId}")
	@Operation(summary = "exitGroup")
	public ResponseEntity<String> exitGroup(
			@PathVariable("groupId") int groupId,
			@RequestHeader("userId") String userId
	) {
		groupService.exit(groupId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@AuthRequired
	@PutMapping("/{groupId}")
	@Operation(summary = "updateGroupInfo")
	public ResponseEntity<Group> updateGroupInfo(
			@PathVariable("groupId") int groupId,
			@RequestBody Group group,
			@RequestHeader("userId") String userId
	) {
		group.setGroupId(groupId);
		if (groupService.update(group,userId))
			return new ResponseEntity<Group>(group, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@AuthRequired
	@DeleteMapping("/{groupId}/delete/{memberId}")
	@Operation(summary = "deleteGroupMember")
	public ResponseEntity<?> deleteGroupMember(
			@PathVariable("groupId") int groupId,
			@RequestBody Group group,
			@PathVariable("memberId") int memberId,
			@RequestHeader("userId") String userId
	) {
		if (groupService.kickOut(groupId, userId, memberId))
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/search")
	@Operation(summary = "searchGroup")
	public ResponseEntity<List<Group>> searchGroup(
			@RequestParam String con
	) {
		List<Group> groups = groupService.search(con);
		if (groups != null)
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
