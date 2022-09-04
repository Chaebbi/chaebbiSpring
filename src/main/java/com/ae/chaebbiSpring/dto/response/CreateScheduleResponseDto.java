package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Schema(description = "일정등록 응답 DTO")
public class CreateScheduleResponseDto {
    @Schema(description = "등록된 일정 id")
    @NotNull
    private int id;
}
