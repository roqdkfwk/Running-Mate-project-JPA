package com.suseok.run.controller;

import com.suseok.run.common.exception.RequiredAuth;
import com.suseok.run.model.entity.Request.CreateGroupReq;
import com.suseok.run.model.entity.Response.CreateGroupRes;
import com.suseok.run.model.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "GroupRestController", description = "그룹 CRUD")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "그룹 생성")
    @RequiredAuth
    public ResponseEntity<CreateGroupRes> createGroup(
            Authentication authentication,
            @RequestBody CreateGroupReq createGroupReq
    ) {
        Long userSeq = Long.valueOf(authentication.getName());
        CreateGroupRes createGroupRes = groupService.createGroup(userSeq, createGroupReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createGroupRes);
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

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @PostMapping("/{groupId}/members")
    @Operation(summary = "그룹 가입")
    public ResponseEntity<Void> joinGroup(
            Long userSeq,
            @PathVariable Long groupId
    ) {
        groupService.joinGroup(userSeq, groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/members")
    @Operation(summary = "그룹 탈퇴")
    public ResponseEntity<Void> leaveGroup(
            Long userSeq,
            @PathVariable Long groupId
    ) {
        groupService.leaveGroup(userSeq, groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/members/{userSeq}")
    @Operation(summary = "멤버 추방")
    public ResponseEntity<Void> kickMember() {
        groupService.kickMember();
        return ResponseEntity.ok().build();
    }
}

