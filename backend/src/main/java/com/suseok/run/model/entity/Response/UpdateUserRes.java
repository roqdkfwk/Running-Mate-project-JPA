package com.suseok.run.model.entity.Response;

import com.suseok.run.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UpdateUserRes {

    private String userId;
    private String userNick;
    private String userName;
    private String email;
    private String img;
    private String address;
    private String phone;
    private boolean exposure;
    private LocalDateTime createdAt;

    public static UpdateUserRes fromEntity(User user) {
        return UpdateUserRes.builder()
                        .userId(user.getUserId())
                        .userNick(user.getUserNick())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .img(user.getImg())
                        .address(user.getAddress())
                        .phone(user.getPhone())
                        .exposure(user.isExposure())
                        .createdAt(user.getCreatedAt())
                        .build();}
}
