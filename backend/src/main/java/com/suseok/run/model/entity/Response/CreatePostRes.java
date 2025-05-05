package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CreatePostRes {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public static CreatePostRes fromEntity(Post post) {
        return CreatePostRes.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getUserNick())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
