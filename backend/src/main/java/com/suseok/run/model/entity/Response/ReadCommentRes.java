package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ReadCommentRes {

    private Long commentId;
    private Long author;
    private String authorNick;
    private String content;
    private LocalDateTime createdAt;

    public static ReadCommentRes fromEntity(Comment comment) {
        return ReadCommentRes.builder()
                .commentId(comment.getCommentId())
                .author(comment.getAuthor())
                .authorNick(comment.getAuthorNick())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
