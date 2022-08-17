package com.ae.chaebbiSpring.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSocialLoginRequestDto {
    @NotNull
    private String accessToken;
}
