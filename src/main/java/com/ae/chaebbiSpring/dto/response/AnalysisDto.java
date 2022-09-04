package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "날짜별 칼로리")
@Data
@AllArgsConstructor
public class AnalysisDto {
    @Schema(name = "date", description = "날짜", nullable = true, example = "222.06.25.", type = "String")
    private String date;
    @Schema(name = "totalCal", description = "총 섭취 칼로리", nullable = true, example = "374", type = "int")
    private int totalCal;
}
