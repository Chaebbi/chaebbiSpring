package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "일반 회원 이메일 중복확인 응답")
public class CheckEmailRes {
    @Schema(description = "일반 회원 이메일 중복 결과", example = "not duplicate")
    @NotNull
    private String isPresent;
}
