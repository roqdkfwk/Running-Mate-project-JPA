package com.suseok.run.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.suseok.run.jwtutill.AuthRequired;
import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.service.BoardService;
import com.suseok.run.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/group/{groupId}/board")
@Tag(name = "BoardRestController", description = "보드CRUD")
public class BoardController {

	private final BoardService bs;
	private final UserService us;

	public BoardController(BoardService bs, UserService us) {
		this.bs = bs;
		this.us = us;
	}

	// 응답을 편하게 하기 위해 상수로 지정
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	private int findWriterSeq(String userId) {
		User user = us.selectById(userId);
		return user.getUserSeq();

	}

	@GetMapping
	@Operation(summary = "groupBoard")
	public ResponseEntity<List<Board>> groupBoard(@PathVariable("groupId") int groupId) {
		List<Board> boards = bs.selectAllByGroupId(groupId);
		
		System.out.println("groupId : " + groupId);
		
		if (boards != null) {
			return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{boardId}")
	@Operation(summary = "boardDetail")
	public ResponseEntity<?> boardDetail(@PathVariable("boardId") int boardId, @RequestHeader("userId") String userId) {
		
		
		Board board = bs.selectById(boardId);
		
		System.out.println(board.toString());
		
		if (board != null) {
			return new ResponseEntity<Board>(board, HttpStatus.OK);}
		return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
	}

	@AuthRequired 
	@PostMapping
	@Operation(summary = "createBoard")
	public ResponseEntity<Board> createBoard(@PathVariable("groupId") int groupId, @RequestBody Board board,
			@RequestHeader("userId") String userId) {
		// userId를 Board 객체에 설정
		board.setGroupId(groupId);
		board.setWriterSeq(findWriterSeq(userId));
		if (bs.insert(board)!=null) {
			
			return new ResponseEntity<Board>(board, HttpStatus.CREATED);}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@AuthRequired 
	@PutMapping("/{boardId}")
	@Operation(summary = "updateBoard")
	public ResponseEntity<?> updateBoard(@PathVariable("boardId") int boardId, @RequestBody Board board,
			@RequestHeader("userId") String userId) {
		
		System.out.println("updateBoard");
		
		// 수정 시에도 userId를 설정할 수 있음
		if (findWriterSeq(userId) != board.getWriterSeq()) {
			System.out.println("유저랑 작성자랑 다름");
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
		if (bs.update(board) != null) {
			System.out.println("find성공");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@AuthRequired 
	@DeleteMapping("/{boardId}")
	@Operation(summary = "deleteBoard")
	public ResponseEntity<?> deleteBoard(@PathVariable("boardId") int boardId,
			@RequestHeader("userId") String userId) {
		System.out.println("요청은 왔음");
		if (findWriterSeq(userId) != bs.selectById(boardId).getWriterSeq()) {
			System.out.println("작성자가 아님");
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);}
		System.out.println("작성자는 맞음");
		if (bs.delete(boardId))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		
		return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/search")
	@Operation(summary = "searchBoard")
	public ResponseEntity<?> searchBoard(@RequestParam String con, @RequestHeader("userId") String userId) {
		List<Board> boards = bs.search(con); // 검색 조회
		if (boards == null || boards.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
	}

	@AuthRequired 
	@PostMapping("/{boardId}/reply")
	@Operation(summary = "createReply")
	public ResponseEntity<?> createReply(@RequestBody Reply reply, @RequestHeader("userId") String userId) {
		if (bs.insertReply(reply))
			return new ResponseEntity<>(HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@AuthRequired 
	@DeleteMapping("/{boardId}/reply/{replyId}")
	@Operation(summary = "deleteReply")
	public ResponseEntity<?> deleteReply(@PathVariable("boardId") int boardId, @PathVariable("replyId") int replyId,
			@RequestHeader("userId") String userId) {
		if (findWriterSeq(userId) != bs.selectReplyById(replyId).getWriterSeq())
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		if (bs.deleteReply(boardId, replyId))
			return new ResponseEntity<>(HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
