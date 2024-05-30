package com.suseok.run.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.service.BoardService;
import com.suseok.run.model.service.GroupService;
import com.suseok.run.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/group")
@Tag(name = "GroupRestController", description = "그룹CRUD")
public class GroupController {

	private final GroupService gs;

	public GroupController(GroupService gs) {
		this.gs = gs;
	}

	@AuthRequired
	@PostMapping
	@Operation(summary = "createGroup")
	public ResponseEntity<Group> createGroup(@RequestBody Group group, @RequestHeader("userId") String userId) {

		System.out.println("Group : " + group.getGroupName());
		System.out.println("userId : " + userId);
		
		if (gs.insert(group,userId))
			return new ResponseEntity<>(group, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{groupId}")
	@Operation(summary = "selectGroupById")
	public ResponseEntity<Group> selectGroupById(@PathVariable("groupId") int groupId) {
		return new ResponseEntity<>(gs.selectById(groupId),HttpStatus.OK);
	}
	
	@GetMapping
	@Operation(summary = "groupList")
	public ResponseEntity<List<Group>> groupList() {
		return new ResponseEntity<List<Group>>(gs.selectAll(),HttpStatus.OK);
	}

//	@AuthRequired
	@GetMapping("/join/{groupId}")
	@Operation(summary = "joinGroup")
	public ResponseEntity<String> joinGroup(@PathVariable("groupId") int groupId,
			@RequestHeader("userId") String userId) {
		gs.join(groupId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@AuthRequired
	@DeleteMapping("/exit/{groupId}")
	@Operation(summary = "exitGroup")
	public ResponseEntity<String> exitGroup(@PathVariable("groupId") int groupId,
			@RequestHeader("userId") String userId) {
		gs.exit(groupId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@AuthRequired
	@PutMapping("/{groupId}")
	@Operation(summary = "updateGroupInfo")
	public ResponseEntity<Group> updateGroupInfo(@PathVariable("groupId") int groupId, @RequestBody Group group,
			@RequestHeader("userId") String userId) {
		// 관리자만 허용 //관리자만 보이는 버튼
	
		group.setGroupId(groupId);
		if (gs.update(group,userId))
			return new ResponseEntity<Group>(group, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@AuthRequired
	@DeleteMapping("/{groupId}/delete/{memberId}")
	@Operation(summary = "deleteGroupMember")
	public ResponseEntity<?> deleteGroupMember(@PathVariable("groupId") int groupId, @RequestBody Group group,
			@PathVariable("memberId") int memberId, @RequestHeader("userId") String userId) {
		// if 유저아이디 == groupid 의 leader이면 바꾸기


		if (gs.kickOut(groupId, userId, memberId))
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/search")
	@Operation(summary = "searchGroup")
	public ResponseEntity<List<Group>> searchGroup(@RequestParam String con) {
		List<Group> groups = gs.search(con);
		if (groups != null)
			return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
