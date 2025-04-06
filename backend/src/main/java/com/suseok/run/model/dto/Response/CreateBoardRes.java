package com.suseok.run.model.dto.Response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CreateBoardRes {

    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}
