package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "하루 일정")
@Data
@AllArgsConstructor
public class ScheduleDto {
    @Schema(description = "일정 id")
    private Long scheduleId;
    @Schema(description = "일정 시작 날짜")
    private String startDate;
    @Schema(description = "일정 제목")
    private String title;
    @Schema(description = "일정 시작 시간")
    private String startTime;

}
