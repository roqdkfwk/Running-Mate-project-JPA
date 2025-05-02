package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.Group;
import com.suseok.run.model.entity.Post;
import com.suseok.run.model.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreatePostReq {

    private String title;
    private String content;

    public Post toEntity(User user, Group group) {
        return Post.builder()
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
