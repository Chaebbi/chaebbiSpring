package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "끼니 식단 기록")
@Data
@AllArgsConstructor
public class DateRecordDto {
    @Schema(description = "사용자의 식단 등록된 id", example = "27", nullable = true, type = "int")
    private int record_id;
    @Schema(description = "사용자의 식단 메뉴", example = "김치찌개", nullable = true, type = "String")
    private String text;
    @Schema(description = "사용자의 식단 날짜 (서버 날짜)", example = "2022.06.03.", nullable = true, type = "String")
    private String date;
    @Schema(description = "사용자의 식단 칼로리", example = "500", nullable = true, type = "String")
    private String calory;
    @Schema(description = "사용자의 식단 탄수화물", example = "15", nullable = true, type = "String")
    private String carb;
    @Schema(description = "사용자의 식단 단백질", example = "10", nullable = true, type = "String")
    private String protein;
    @Schema(description = "사용자의 식단 지방", example = "5", nullable = true, type = "String")
    private String fat;
    @Schema(description = "사용자의 식단 작성 날짜(직접 입력)", example = "2022.06.03.", nullable = true, type = "String")
    private String rdate;
    @Schema(description = "사용자의 식단 작성 시간(직접 입력)", example = "08:00", nullable = true, type = "String")
    private String rtime;
    @Schema(description = "사용자의 식단 섭취한 양", example = "153.3", nullable = true, type = "Double")
    private Double amount;
}
