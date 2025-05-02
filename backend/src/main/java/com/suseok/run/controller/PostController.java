package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;
import com.suseok.run.model.entity.Response.ReadPostRes;
import com.suseok.run.model.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups/{groupId}/posts")
@Tag(name = "PostRestController", description = "게시글 CRUD")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	@Operation(summary = "게시글 작성")
	public ResponseEntity<Long> createPost(
			Authentication authentication,
			@PathVariable Long groupId,
			@RequestBody CreatePostReq createPostReq
	) {
		Long userSeq = Long.valueOf(authentication.getName());
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(postService.createPost(userSeq, groupId, createPostReq));
	}

	// Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
	@PatchMapping("/{postId}")
	@Operation(summary = "게시글 수정")
	public ResponseEntity<Void> updatePost(
			@RequestParam Long postId,
			@RequestBody UpdatePostReq updatePostReq
	) {
		postService.updatePost(postId, updatePostReq);
		return ResponseEntity.status(200).build();
	}

	// Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
	@DeleteMapping
	@Operation(summary = "게시글 삭제")
	public ResponseEntity<Void> deletePost(
			@RequestParam Long userSeq,
			@RequestParam Long postId
	) {
		postService.deletePost(userSeq, postId);
		return ResponseEntity.status(200).build();
	}

	@GetMapping("/{postId}")
	@Operation(summary = "게시글 상세보기")
	public ResponseEntity<ReadPostRes> getPost(@PathVariable("postId") Long postId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(postService.getPost(postId));
	}
}
