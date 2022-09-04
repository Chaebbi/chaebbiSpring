package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "일반 로그인 응답 DTO")
public class GeneralLoginResDto {
    @Schema(description = "로그인한 사용자의 id")
    @NotNull
    private Long userId;
    @Schema(description = "로그인한 사용자의 jwt 토큰")
    @NotNull
    private String token;
}
