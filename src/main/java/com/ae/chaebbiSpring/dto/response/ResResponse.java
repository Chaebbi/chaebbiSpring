package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "재료 조회 응답 DTO")
public class ResResponse<T> {
    @Schema(description = "요청한 데이터의 개수")
    private Integer count;
    @Schema(description = "요청한 데이터")
    private T data;
}
