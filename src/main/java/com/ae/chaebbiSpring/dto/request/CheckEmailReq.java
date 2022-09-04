package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "일반 회원 이메일 중복확인 요청")
public class CheckEmailReq {
    @Schema(description = "중복 확인하려는 회원의 이메일", example = "chaebbi@gmail.com")
    @NotNull
    private String email;
}
