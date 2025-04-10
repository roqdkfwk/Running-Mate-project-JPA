package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.Comment;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentReq {

    private String content;

    public Comment toEntity(
            User author,
            Post post
    ) {
        return Comment.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .author(author)
                .post(post)
                .build();
    }
}
