package com.suseok.run.model.service;

import com.suseok.run.common.NotFoundException;
import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.dto.Request.CreateBoardReq;
import com.suseok.run.model.dto.Request.UpdateBoardReq;
import com.suseok.run.model.dto.Response.UpdateBoardRes;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;

	/**
	 * 게시글 작성
	 * @return
	 */
	@Override
	public Long createBoard(
			Long userSeq,
			Long groupId,
			CreateBoardReq createBoardReq) {
		// 1. 게시글 작성자

		// 2. 게시글을 작성할 게시판(그룹)

		// 3. 게시글 생성 및 저장
		Board board = createBoardReq.toEntity(new User(), new Group());
		boardRepository.save(board);
		return board.getBoardId();
	}

	/**
	 * 게시글 수정
	 */
	@Override
	public void updateBoard(
			Long boardId,
			UpdateBoardReq updateBoardReq
	) {
		Board board = boardRepository.findById(boardId).orElseThrow(
				() -> new NotFoundException("존재하지 않는 게시글입니다.")
		);

		updateBoardReq.toEntity(board, updateBoardReq);
		boardRepository.save(board);
	}

	/**
	 * 게시글 삭제
	 * @param boardId
	 * @param userSeq
	 */
	@Override
	public void deleteBoard(Long boardId, Long userSeq) {

	}

	/**
	 * 게시판 내 게시글 목록 조회
	 * @param groupId
	 * @return
	 */
	@Override
	public List<Board> getBoardsByGroup(Long groupId) {
		return List.of();
	}

	/**
	 * 게시글 상세 조회
	 * @param boardId
	 * @return
	 */
	@Override
	public Board getBoard(Long boardId) {
		return boardRepository.findById(boardId).orElseThrow(
				() -> new NotFoundException("존재하지 않는 게시글입니다.")
		);
	}

	/**
	 * 게시글 검색
	 * @param con
	 * @return
	 */
	@Override
	public List<Board> searchBoards(String con) {

		return new ArrayList<>();
	}

}
