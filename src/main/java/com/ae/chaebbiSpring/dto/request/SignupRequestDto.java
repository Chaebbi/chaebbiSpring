package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원 등록 request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @Schema(name = "name", description = "사용자 이름", nullable = false, example = "홍길동", type = "String")
    private String name;
    @Schema(name = "nickname", description = "사용자 닉네임", nullable = false, example = "길동이", type = "String")
    private String nickname;
    @Schema(name = "age", description = "사용자 나이", nullable = false, example = "25", type = "int")
    private String age;
    @Schema(name = "gender", description = "사용자 성별(0:남성, 1:여성)", nullable = false, example = "0", type = "int")
    private String gender;
    @Schema(name = "height", description = "사용자 키", nullable = false, example = "175", type = "String")
    private String height;
    @Schema(name = "weight", description = "사용자 몸무게", nullable = false, example = "65", type = "String")
    private String weight;
    @Schema(name = "activity", description = "사용자 활동점수", nullable = false, example = "33", type = "int")
    private String activity;
}