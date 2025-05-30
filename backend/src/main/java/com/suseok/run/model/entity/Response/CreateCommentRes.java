package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CreateCommentRes {

    private Long commentId;
    private Long author;
    private String authorNick;
    private String content;
    private LocalDateTime createdAt;

    public static CreateCommentRes fromEntity(Comment comment) {
        return CreateCommentRes.builder()
                .commentId(comment.getCommentId())
                .author(comment.getAuthor())
                .authorNick(comment.getAuthorNick())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
