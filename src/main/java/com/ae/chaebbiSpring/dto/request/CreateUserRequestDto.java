package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일반 회원가입 요청 DTO")
public class CreateUserRequestDto {
    @Schema(description = "회원가입하려는 회원의 이메일")
    @NotNull
    private String email;
    @Schema(description = "회원가입하려는 회원의 비밀번호 (5자 이상 20자 미만)")
    private String pwd;
    @Schema(description = "회원가입하려는 회원의 이름")
    private String name;
    @Schema(description = "회원가입하려는 회원의 닉네임")
    private String nickname;
    @Schema(description = "회원가입하려는 회원의 성별 0:남자 1:여자")
    private int gender;
    @Schema(description = "회원가입하려는 회원의 나이")
    private int age;
    @Schema(description = "회원가입하려는 회원의 키")
    private String height;
    @Schema(description = "회원가입하려는 회원의 몸무게")
    private String weight;
    @Schema(description = "회원가입하려는 회원의 활동점수 (25,33,40)")
    private int activity;
}
