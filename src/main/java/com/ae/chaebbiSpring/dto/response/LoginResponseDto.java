package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "카카오 로그인 response")
@Data
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(name = "userId", description = "사용자 아이디", nullable = false, example = "3", type = "Long")
    @NotNull
    private Long userId;
    @Schema(name = "token", description = "사용자 jwt 토큰", nullable = false, example = "fawrijgikxmawi5431", type = "String")
    @NotNull
    private String token;
    @Schema(name = "isSignup", description = "온보딩 띄워야 하는 여부 (true가 띄워야 함)", nullable = false, example = "true", type = "boolean")
    @NotNull
    private boolean isSignup; // 온보딩 띄워야 하는 여부 (true가 띄워야함)
}

