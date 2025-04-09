package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;
import com.suseok.run.model.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups/{groupId}/posts")
@Tag(name = "PostRestController", description = "게시글 CRUD")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	@Operation(summary = "게시글 작성")
	public ResponseEntity<Long> createPost(
			@RequestParam Long groupId,
			@RequestParam Long userSeq,
			@RequestBody CreatePostReq createPostReq
	) {
		return ResponseEntity
				.status(201)
				.body(postService.createPost(userSeq, groupId, createPostReq));
	}

	@PatchMapping("/{postId}")
	@Operation(summary = "게시글 수정")
	public ResponseEntity<Void> updatePost(
			@RequestParam Long postId,
			@RequestBody UpdatePostReq updatePostReq
	) {
		postService.updatePost(postId, updatePostReq);
		return ResponseEntity.status(200).build();
	}

	@GetMapping("/{postId}")
	@Operation(summary = "게시글 상세보기")
	public ResponseEntity<Void> getPost(@PathVariable("postId") Long postId) {
		return ResponseEntity.status(200).build();
	}
}
