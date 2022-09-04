package com.ae.chaebbiSpring.dto.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(description = "날짜별 조회 response")
@Data
@AllArgsConstructor
public class DateRecordResponseDto {
    @Schema(description = "해당 날짜에 섭취한 총 칼로리", example = "500", nullable = true, type = "int")
    private int totalCalory;
    @Schema(description = "해당 날짜에 섭취한 총 탄수화물", example = "50", nullable = true, type = "int")
    private int totalCarb;
    @Schema(description = "해당 날짜에 섭취한 총 단백질", example = "40", nullable = true, type = "int")
    private int totalPro;
    @Schema(description = "해당 날짜에 섭취한 총 지방", example = "30", nullable = true, type = "int")
    private int totalFat;
    @Schema(description = "사용자의 하루 권장 칼로리", example = "1500", nullable = true, type = "int")
    private int recommCalory;
    @Schema(description = "사용자의 하루 권장 탄수화물양", example = "50", nullable = true, type = "int")
    private int recommCarb;
    @Schema(description = "사용자의 하루 권장 단백질 양", example = "40", nullable = true, type = "int")
    private int recommPro;
    @Schema(description = "사용자의 하루 권장 지방 양", example = "30", nullable = true, type = "int")
    private int recommFat;
    @Schema(description = "사용자의 하루 식단 기록", nullable = true)
    private List<RecordsDto> records;
}
