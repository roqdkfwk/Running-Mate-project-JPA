package com.suseok.run.model.service;

import java.util.List;

import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;

public interface BoardService {

	List<Board> search(String con);

	Board selectById(int boardId);

	List<Board> selectAllByGroupId(int groupId);

	boolean delete(int boardId);

	Board update(Board board);

	Board insert(Board board);
	
	Reply selectReplyById(int replyId);

	boolean deleteReply(int boardId, int replyId);

	boolean insertReply(Reply reply);

}
