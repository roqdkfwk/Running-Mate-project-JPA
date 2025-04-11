package com.suseok.run.common.exception;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "인증 실패")
})
@ApiImplicitParams
        ({
        @ApiImplicitParam(name = "Authorization", value = "Bearer 로 시작하는 JWT 토큰 필요", required = false, dataTypeClass = String.class, paramType = "header")
})
public @interface RequiredAuth {
}
