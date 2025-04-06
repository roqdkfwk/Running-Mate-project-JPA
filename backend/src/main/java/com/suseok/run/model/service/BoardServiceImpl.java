package com.suseok.run.model.service;

import com.suseok.run.model.dao.BoardDao;
import com.suseok.run.model.dao.UserDao;
import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	// TODO BoardReposity
	private final BoardDao boardDao;
	
	private final UserDao userDao;
	
	@Override
	public List<Board> selectAllByGroupId(int groupId) {
		
		 List<Board> boards = boardDao.selectAllByGroupId(groupId);
		 
		 for(int i=0; i<boards.size(); i++) {
			String writerNick = userDao.selectBySeq(boards.get(i).getWriterSeq()).getUserNick();
			boards.get(i).setWriterNick(writerNick);
		 }
		return boards;
	}

	@Override
	public Board selectById(int boardId) {
		Board board = boardDao.selectById(boardId);
		String writerNick = userDao.selectBySeq(board.getWriterSeq()).getUserNick();
		board.setWriterNick(writerNick);
		return board;
	}
	
	@Override
	public List<Board> search(String con) {
		return boardDao.search(con);
	}

	@Override
	public void deleteBoard(int userSeq, int boardId) {
		boardDao.deleteBoard(userSeq, boardId);
	}

	@Override
	public Board update(Board board) {
		boardDao.update(board);
		
		String writerNick = userDao.selectBySeq(board.getWriterSeq()).getUserNick();
		board.setWriterNick(writerNick);
		return board;
	}

	@Override
	public Board insert(Board board) {
		boardDao.insert(board);
		return board;
	}

	@Override
	public boolean deleteReply(int boardId, int replyId) {
		return boardDao.deleteReply(boardId,replyId);
	}

	@Override
	public boolean insertReply(Reply reply) {
		return boardDao.insertReply(reply);
	}

	@Override
	public Reply selectReplyById(int replyId) {
		return boardDao.selectReplyById(replyId);
	}
}
