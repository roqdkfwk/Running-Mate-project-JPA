package com.suseok.run.common.exception;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "인증 실패")
})
@Parameters({
        @Parameter(name = "Authorization",
                description = "Bearer 로 시작하는 JWT 토큰 필요",
                required = false,
                in = ParameterIn.HEADER)
})
@SecurityRequirement(name = "JWT")
public @interface RequiredAuth {
}