package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일반 로그인 요청 DTO")
public class GeneralLoginReqDto {
    @Schema(description = "로그인 하려는 회원의 이메일", example = "chaebbi@gmail.com")
    @NotNull
    private String email;
    @Schema(description = "로그인 하려는 회원의 비밀번호", example = "12345678")
    @NotNull
    private String pwd;
}
