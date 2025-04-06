package com.suseok.run.model.dto.Request;

import com.suseok.run.model.dto.Board;

import java.time.LocalDateTime;

public class UpdateBoardReq {

    private String title;

    private String content;

    private LocalDateTime updatedAt;

    public void toEntity(
            Board board,
            UpdateBoardReq updateBoardReq
    ) {
        board.setTitle(updateBoardReq.title);
        board.setContent(updateBoardReq.content);
        board.setUpdatedAt(LocalDateTime.now());
    }
}
