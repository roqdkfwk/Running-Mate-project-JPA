package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginRes {

    private String userName;
    private String userNick;
    private String email;
    private LocalDateTime createdAt;

    public static LoginRes fromEntity(User user) {
        return LoginRes.builder()
                .userName(user.getUserName())
                .userNick(user.getUserNick())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
