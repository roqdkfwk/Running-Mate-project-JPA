package com.suseok.run.controller;

import com.suseok.run.model.entity.Comment;
import com.suseok.run.model.entity.User;
import com.suseok.run.model.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "TestRestController", description = "테스트용 API")
public class TestController {

    private final TestService testService;

    @GetMapping("/users")
    @Operation(summary = "유저 조회")
    public ResponseEntity<Void> getUser(Long userSeq) {
        User user = testService.getUser(userSeq);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/comments")
    @Operation(summary = "댓글 조회")
    public ResponseEntity<Void> getUser(Long postId, int count) {
        List<Comment> commentList = testService.getComments(postId, count);
//        for (Comment com : commentList) {
//            System.out.println(com.getCommentId() + " " +  com.getAuthor());
//        }
        return ResponseEntity.ok().build();
    }
}
