package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "상세 조회 request")
@Data
public class DetailRecordRequestDto {
    @Schema(description = "조회하고 싶은 기록 id", example = "27", nullable = false, type = "int")
    @NotNull
    private int record_id;
}
