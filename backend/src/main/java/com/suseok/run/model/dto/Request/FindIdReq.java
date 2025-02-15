package com.suseok.run.model.dto.Request;

import lombok.Data;
import lombok.NonNull;

@Data
public class FindIdReq {
    @NonNull
    String userName;
    @NonNull
    String phoneOrEmail;
}
