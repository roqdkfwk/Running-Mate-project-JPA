package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadPostRes {

    private Long postId;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public static ReadPostRes fromEntity(Post post) {
        return ReadPostRes.builder()
                .postId(post.getPostId())
                .author(post.getAuthor().getUserNick())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
