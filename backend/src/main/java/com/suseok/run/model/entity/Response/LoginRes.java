package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRes {

    private User user;
    private String accessToken;

    public static LoginRes fromEntity(
            User user,
            String accessToken
    ) {
        return LoginRes.builder()
                .user(user)
                .accessToken(accessToken)
                .build();
    }
}
