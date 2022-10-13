package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "닉네임 중복 확인 응답 dto", description = "닉네임 중복 확인 여부 결과")
public class UserNicknameResponseDto {
    @Schema(description = "닉네임 중복 확인 결과", example = "true")
    private boolean isExist;
    @Schema(description = "닉네임 중복 확인 결과 메시지", example = "이미 존재하는 닉네임입니다.")
    private String note;
}
