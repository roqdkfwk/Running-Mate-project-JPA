package com.suseok.run.model.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suseok.run.model.dao.BoardDao;
import com.suseok.run.model.dao.UserDao;
import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;

@Service
public class BoardServiceImpl implements BoardService {

	// TODO BoardReposity
	@Autowired
	BoardDao bd;
	
	@Autowired
	UserDao ud;
	
	@Override
	public List<Board> selectAllByGroupId(int groupId) {
		
		 List<Board> boards = bd.selectAllByGroupId(groupId);
		 
		 for(int i=0; i<boards.size(); i++) {
			String writerNick = ud.selectBySeq(boards.get(i).getWriterSeq()).getUserNick();
			boards.get(i).setWriterNick(writerNick);
		 }
		return boards;
	}

	@Override
	public Board selectById(int boardId) {
		Board board = bd.selectById(boardId);
		String writerNick = ud.selectBySeq(board.getWriterSeq()).getUserNick();
		board.setWriterNick(writerNick);
		return board;
	}
	
	@Override
	public List<Board> search(String con) {
		return bd.search(con);
	}

	@Override
	public boolean delete(int boardId) {
		return bd.delete(boardId);
	}

	@Override
	public Board update(Board board) {
		bd.update(board);
		
		String writerNick = ud.selectBySeq(board.getWriterSeq()).getUserNick();
		board.setWriterNick(writerNick);
		return board;
	}

	@Override
	public Board insert(Board board) {
		bd.insert(board);
		return board;
	}

	@Override
	public boolean deleteReply(int boardId, int replyId) {
		return bd.deleteReply(boardId,replyId);
	}

	@Override
	public boolean insertReply(Reply reply) {
		return bd.insertReply(reply);
	}

	@Override
	public Reply selectReplyById(int replyId) {
		return bd.selectReplyById(replyId);
	}

	


}
