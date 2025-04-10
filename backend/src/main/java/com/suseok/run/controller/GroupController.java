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

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @PostMapping
    @Operation(summary = "그룹 생성")
    public ResponseEntity<Void> createGroup(
            Long userSeq,
            @RequestBody CreateGroupReq createGroupReq
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

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @DeleteMapping
    @Operation(summary = "그룹 삭제")
    public ResponseEntity<Void> deleteGroup(
            Long userSeq,
            @PathVariable Long groupId
    ) {
        groupService.deleteGroup(userSeq, groupId);
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

