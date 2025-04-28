package com.suseok.run.model.entity.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {

    private LoginRes loginRes;
    private String accessToken;
}
