package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(name = "RecordResponseDto", description = "생성된 식단 기록")
@Data
@AllArgsConstructor
public class RecordResponseDto {
    @Schema(name = "id", description = "생성된 식단 기록 id", nullable = false, example = "0", type = "int")
    @NotNull
    private int id;
}
