package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.CreateCommentReq;
import com.suseok.run.model.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "CommentRestController", description = "댓글 CRUD")
@RequestMapping("/groups/{groupId}/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResponseEntity<Long> createComment(
            Long userSeq,
            @PathVariable Long postId,
            @RequestBody CreateCommentReq createCommentReq
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComent(userSeq, postId, createCommentReq));
    }

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @PatchMapping
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> updateComment(
            Long userSeq,
            Long commentId,
            @RequestBody String content
    ) {
        commentService.updateComment(userSeq, commentId, content);
        return ResponseEntity.status(200).build();
    }

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @DeleteMapping
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            Long userSeq,
            Long commentId
    ) {
        commentService.deleteComment(userSeq, commentId);
        return ResponseEntity.status(200).build();
    }

    // Todo: 내가 작성한 댓글 표시 기능, 신고 기능, 좋아요 기능, 정렬 기능, 대댓글 기능
}
