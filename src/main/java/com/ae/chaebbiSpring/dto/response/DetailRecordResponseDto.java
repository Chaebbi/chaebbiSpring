package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "상세 조회 response")
@Data
@AllArgsConstructor
public class DetailRecordResponseDto {
    @Schema(description = "사용자 이름", example = "홍길동", nullable = true, type = "String")
    private String name;
    @Schema(description = "음식 이름", example = "비빔밥", nullable = true, type = "String")
    private String text;
    @Schema(description = "음식 칼로리", example = "114", nullable = true, type = "String")
    private String cal;
    @Schema(description = "음식 탄수화물", example = "30", nullable = true, type = "String")
    private String carb;
    @Schema(description = "음식 단백질", example = "15", nullable = true, type = "String")
    private String protein;
    @Schema(description = "음식 지방", example = "10", nullable = true, type = "String")
    private String fat;
    @Schema(description = "음식 이미지사진url", example = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_x4-JMp0IoFsWNoXT_Sk9nS7qSWKyH8lqqg&usqp=CAU", nullable = true, type = "String")
    private String image_url;
    @Schema(description = "음식 기록날짜", example = "2022.06.22.", nullable = true, type = "String")
    private String date;
    @Schema(description = "음식 기록시간", example = "08:00", nullable = true, type = "String")
    private String time;
    @Schema(description = "음식 기록양", example = "300", nullable = true, type = "Double")
    private Double amount;
    @Schema(description = "끼니", example = "점심", nullable = true)
    private int meal;
}
