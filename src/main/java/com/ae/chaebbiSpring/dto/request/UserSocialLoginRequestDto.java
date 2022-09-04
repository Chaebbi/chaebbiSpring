package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Schema(description = "카카오 로그인 request")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialLoginRequestDto {
    @Schema(name = "accessToken", description = "카카오 accessToken", nullable = false, example = "fdisajfi3294109", type = "String")
    @NotNull
    private String accessToken;
}
