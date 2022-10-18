package com.ae.chaebbiSpring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "문제 식이에 대한 제안음식 리스트")
@Data
@AllArgsConstructor
public class SuggestionsDto {
    @Schema(name="problemId", description = "문제되는 식사 습관의  id")
    private Long problemId;
    @Schema(name="foodUrl", description = "문제되는 식사 습관의  제안음식 url")
    private String foodUrl;
    @Schema(name="foodName", description = "문제되는 식사 습관의  제안음식 이름")
    private String foodName;
}
