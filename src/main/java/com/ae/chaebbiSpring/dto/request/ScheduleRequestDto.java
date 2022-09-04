package com.ae.chaebbiSpring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Getter
@Schema(description = "일정 등록 요청 DTO")
public class ScheduleRequestDto {
    @Schema(description = "등록할 일정의 제목")
    private String title;
    @Schema(description = "등록할 일정의 대분류", example = "서울특별시")
    private String wide;
    @Schema(description = "등록할 일정의 중분류", example = "강남구")
    private String middle;
    @Schema(description = "등록할 일정의 시작 날짜", example = "2022-08-30")
    private String sdate;
    @Schema(description = "등록할 일정의 종료 날짜", example = "2022-08-31")
    private String edate;
    @Schema(description = "등록할 일정의 시작 시간", example = "20:08:36")
    private String stime;
    @Schema(description = "등록할 일정의 종료 시간", example = "23:08:36")
    private String etime;
}
