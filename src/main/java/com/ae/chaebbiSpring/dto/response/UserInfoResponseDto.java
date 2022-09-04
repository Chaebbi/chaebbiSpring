package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "회원 정보 조회 response")
@Data
@AllArgsConstructor
public class UserInfoResponseDto {
    @Schema(name = "name", description = "사용자 이름", nullable = true, example = "홍길동", type = "String")
    private String name;
    @Schema(name = "gender", description = "사용자 성별 (0: 남성, 1: 여성)", nullable = true, example = "0", type = "int")
    private int gender;
    @Schema(name = "age", description = "사용자 나이", nullable = true, example = "25", type = "int")
    private int age;
    @Schema(name = "height", description = "사용자 키", nullable = true, example = "175", type = "String")
    private String height;
    @Schema(name = "weight", description = "사용자 몸무게", nullable = true, example = "65", type = "String")
    private String weight;
    @Schema(name = "icon", description = "사용자 프사 몇번째인지 지정번호", nullable = true, example = "0", type = "int")
    private int icon;
    @Schema(name = "activity", description = "사용자 활동 점수", nullable = true, example = "33", type = "int")
    private int activity;
}
