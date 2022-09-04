package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "회원 정보 수정 request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    @Schema(name = "age", description = "사용자 수정 나이", nullable = false, example = "26", type = "int")
    private String age;
    @Schema(name = "height", description = "사용자 수정 키", nullable = false, example = "178", type = "String")
    private String height;
    @Schema(name = "weight", description = "사용자 수정 몸무게", nullable = false, example = "78", type = "String")
    private String weight;
    @Schema(name = "activity", description = "사용자 수정 활동점수", nullable = false, example = "40", type = "int")
    private String activity;
}
