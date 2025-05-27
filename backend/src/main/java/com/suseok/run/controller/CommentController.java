package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.CreateCommentReq;
import com.suseok.run.model.entity.Response.CreateCommentRes;
import com.suseok.run.model.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "CommentRestController", description = "댓글 CRUD")
@RequestMapping("/api/groups/{groupId}/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CreateCommentRes> createComment(
            Authentication authentication,
            @PathVariable("postId") Long postId,
            @RequestBody CreateCommentReq createCommentReq
    ) {
        Long userSeq = Long.valueOf(authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(userSeq, postId, createCommentReq));
    }

    @GetMapping
    @Operation(summary = "댓글 목록 조회")
    public ResponseEntity<List<CreateCommentRes>> readAllComments(
            @PathVariable("postId") Long postId
    ) {
        List<CreateCommentRes> commentList = commentService.readAllComments(postId);
        System.out.println("댓글의 개수 : " + commentList.size());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentList);
    }

//    @GetMapping
//    @Operation(summary = "댓글 조회", description = "page(0부터 시작)와 size 파라미터로 페이징 조회")
//    public ResponseEntity<Page<ReadCommentRes>> getGroupList(
//            @ParameterObject
//            @PageableDefault(
//                    page      = 0,
//                    size      = 10,
//                    sort      = "pace"
//            ) Pageable pageable
//    ) {
//        Page<ReadCommentRes> page = commentService.getCommentList(pageable);
//        return ResponseEntity.ok(page);
//    }

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
