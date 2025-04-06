package com.suseok.run.controller;

import com.suseok.run.model.dto.Request.CreateBoardReq;
import com.suseok.run.model.dto.Request.UpdateBoardReq;
import com.suseok.run.model.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/{groupId}/board")
@Tag(name = "BoardRestController", description = "게시글 CRUD")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	@Operation(summary = "게시글 작성")
	public ResponseEntity<Long> createBoard(
			@RequestParam Long groupId,
			@RequestParam Long userSeq,
			@RequestBody CreateBoardReq createBoardReq
	) {
		return ResponseEntity
				.status(201)
				.body(boardService.createBoard(userSeq, groupId, createBoardReq));
	}

	@PatchMapping("/{boardId}")
	@Operation(summary = "게시글 수정")
	public ResponseEntity<Void> updateBoard(
			@RequestParam Long boardId,
			@RequestBody UpdateBoardReq updateBoardReq
	) {
		boardService.updateBoard(boardId, updateBoardReq);
		return ResponseEntity.status(200).build();
	}

	@GetMapping("/{boardId")
	@Operation(summary = "게시글 상세보기")
	public ResponseEntity<Void> getBoard(@PathVariable("boardId") Long boardId) {
		return ResponseEntity.status(200).build();
	}
}
