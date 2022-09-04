package com.ae.chaebbiSpring.dto.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(description = "하루 식단 기록")
@Data
@AllArgsConstructor
public class RecordsDto {
    @Schema(description = "아침, 점심, 저녁 구분", example = "0", nullable = true, type = "int")
    private int meal; // 아침, 점심, 저녁 구분
    @Schema(description = "매끼니에 섭취한 칼로리 총량", example = "114", nullable = true, type = "int")
    private int mCal; // 한끼니의 총 칼로리
    @Schema(description = "매끼니에 해당하는 식단 기록들", nullable = true)
    private List<DateRecordDto> record; // 한끼니의 총 식단
}
