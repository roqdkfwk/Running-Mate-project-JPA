package com.suseok.run.model.service;

import com.suseok.run.model.entity.Board;
import com.suseok.run.model.entity.Request.CreateBoardReq;
import com.suseok.run.model.entity.Request.UpdateBoardReq;

import java.util.List;

public interface BoardService {

	Long createBoard(Long userSeq, Long groupId, CreateBoardReq createBoardReq);

	void updateBoard(Long boardId, UpdateBoardReq updateBoardReq);

	void deleteBoard(Long boardId, Long userSeq);

	Board getBoard(Long boardId);

	List<Board> getBoardsByGroup(Long groupId);

	List<Board> searchBoards(String con);
}
