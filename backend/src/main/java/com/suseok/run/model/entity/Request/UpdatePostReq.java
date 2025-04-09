package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.Post;

import java.time.LocalDateTime;

public class UpdatePostReq {

    private String title;

    private String content;

    private LocalDateTime updatedAt;

    public void toEntity(
            Post post,
            UpdatePostReq updatePostReq
    ) {
        post.setTitle(updatePostReq.title);
        post.setContent(updatePostReq.content);
        post.setUpdatedAt(LocalDateTime.now());
    }
}
