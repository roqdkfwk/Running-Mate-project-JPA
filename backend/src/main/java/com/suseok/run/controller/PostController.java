package com.suseok.run.controller;

import com.suseok.run.model.entity.Request.CreatePostReq;
import com.suseok.run.model.entity.Request.UpdatePostReq;
import com.suseok.run.model.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups/{groupId}/posts")
@Tag(name = "PostRestController", description = "게시글 CRUD")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	// Todo: userSeq는 SpringSecurity + JWT 적용 후 Authentication에서 추출
	@PostMapping
	@Operation(summary = "게시글 작성")
	public ResponseEntity<Long> createPost(
			@RequestParam Long userSeq,
			@RequestParam Long groupId,
			@RequestBody CreatePostReq createPostReq
	) {
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
	public ResponseEntity<Void> getPost(@PathVariable("postId") Long postId) {
		return ResponseEntity.status(200).build();
	}
}
