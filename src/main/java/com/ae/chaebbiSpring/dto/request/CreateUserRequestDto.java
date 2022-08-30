package com.ae.chaebbiSpring.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CreateUserRequestDto {
    @NotNull
    @Email(message = "올바른 이메일 주소 입력하세요")
    private String email;
    @Size(min = 5, max =20, message="비밀번호는 5자이상 20자 이하로 입력")
    private String pwd;
    private String name;
    private int gender;
    private int age;
    private String height;
    private String weight;
    private int activity;
}
