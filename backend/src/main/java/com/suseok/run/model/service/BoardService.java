package com.suseok.run.model.service;

import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;

import java.util.List;

public interface BoardService {

	List<Board> search(String con);

	Board selectById(int boardId);

	List<Board> selectAllByGroupId(int groupId);

	void deleteBoard(int userSeq, int boardId);

	Board update(Board board);

	Board insert(Board board);
	
	Reply selectReplyById(int replyId);

	boolean deleteReply(int boardId, int replyId);

	boolean insertReply(Reply reply);
}
