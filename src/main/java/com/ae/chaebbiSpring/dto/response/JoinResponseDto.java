package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "일반 회원가입 응답")
public class JoinResponseDto {
    @Schema(description = "회원가입 한 회원의 id")
    @NotNull
    private Long userId;
}
