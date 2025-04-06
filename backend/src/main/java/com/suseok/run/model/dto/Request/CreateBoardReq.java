package com.suseok.run.model.dto.Request;

import com.suseok.run.model.dto.Board;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.dto.User;

import java.time.LocalDateTime;

public class CreateBoardReq {

    private String title;
    private String content;

    public Board toEntity(User user, Group group) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .img(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .author(user)
                .group(group)
                .build();
    }
}
