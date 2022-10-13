package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "닉네임 중복 확인 요청 DTO")
public class UserNicknameRequestDto {
    @Schema(description = "중복 확인 하려는 닉네임", example = "스폰지밥")
    private String nickname;
}
