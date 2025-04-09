package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.CreateGroupReq;
import com.suseok.run.model.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@Tag(name = "GroupRestController", description = "그룹 CRUD")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "그룹 생성")
    public ResponseEntity<Void> createGroup(
            Long userSeq,
            CreateGroupReq createGroupReq
    ) {
        groupService.createGroup(userSeq, createGroupReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    @Operation(summary = "그룹 수정")
    public ResponseEntity<Void> updateGroup() {
        groupService.updateGroup();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "그룹 삭제")
    public ResponseEntity<Void> deleteGroup() {
        groupService.deleteGroup();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/members")
    @Operation(summary = "그룹 가입")
    public ResponseEntity<Void> joinGroup() {
        groupService.joinGroup();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/members")
    @Operation(summary = "그룹 탈퇴")
    public ResponseEntity<Void> leaveGroup() {
        groupService.leaveGroup();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/members/{userSeq}")
    @Operation(summary = "멤버 추방")
    public ResponseEntity<Void> kickMember() {
        groupService.kickMember();
        return ResponseEntity.ok().build();
    }
}

