package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "식단 삭제 요청 DTO")
public class RecordDeleteRequestDto {
    @Schema(description = "삭제하려는 식단 기록 id", example = "346")
    @NotNull
    private Long recordId;
}
