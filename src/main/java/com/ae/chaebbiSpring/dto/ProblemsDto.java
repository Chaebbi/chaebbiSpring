package com.ae.chaebbiSpring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "문제 식이 리스트")
@Data
@AllArgsConstructor
public class ProblemsDto {
    @Schema(name="problemId", description = "문제되는 식사 습관의  id")
    private int problemId;
    @Schema(name="count", description = "문제되는 식사 습관의 횟수 ")
    private int cnt;

}
