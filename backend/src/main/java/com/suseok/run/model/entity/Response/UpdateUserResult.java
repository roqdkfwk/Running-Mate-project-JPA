package com.suseok.run.model.entity.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateUserResult {

    private UpdateUserRes updateUserRes;
    private String acccessToken;
}
