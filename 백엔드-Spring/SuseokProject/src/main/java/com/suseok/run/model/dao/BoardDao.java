package com.suseok.run.model.dao;

import java.util.List;

import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;

public interface BoardDao {
	// CRUD : insert, selectAll, update, delete

	boolean insert(Board board);

	List<Board> selectAllByGroupId(int groupId);
	
	Board selectById(int boardId);

	boolean update(Board board);

	boolean delete(int boardId);

	List<Board> search(String con);

	boolean deleteReply(int boardId, int replyId);

	boolean insertReply(Reply reply);

	Reply selectReplyById(int replyId);
}
