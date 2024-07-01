package com.suseok.run.model.service;

import java.util.List;

import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;

public interface BoardService {

	// 게시글 생성
	Board insert(Board board);
	
	// 게시글 조회
	Board selectById(int boardId);
	
	// 전체 게시글 조회
	List<Board> selectAllByGroupId(int groupId);
	
	// 게시글 수정
	Board update(Board board);
	
	// 게시글 삭제
	boolean delete(int boardId);
	
	// 게시글 검색
	List<Board> search(String con);
	
	// 댓글 생성
	boolean insertReply(Reply reply);
	
	// 댓글 삭제
	boolean deleteReply(int boardId, int replyId);
	
	// 댓글 검색?
	Reply selectReplyById(int replyId);
}
