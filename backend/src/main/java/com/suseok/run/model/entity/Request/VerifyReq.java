package com.suseok.run.model.entity.Request;

import lombok.Data;
import lombok.NonNull;

@Data
public class VerifyReq {
    @NonNull
    private String email;
    @NonNull
    private String code;
}
