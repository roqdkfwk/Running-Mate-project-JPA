package com.suseok.run.model.entity.Request;

import com.suseok.run.model.entity.User;

import java.time.LocalDateTime;

public class CreateUserReq {

    private String userId;

    private String userPw;

    private String userName;

    private String userNick;

    private String email;

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .userPw(this.userPw)
                .userName(this.userName)
                .userNick(this.userNick)
                .email(this.email)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
