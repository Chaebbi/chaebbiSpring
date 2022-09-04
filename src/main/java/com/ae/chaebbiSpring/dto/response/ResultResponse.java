package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "음식점 조회 응답 DTO")
public class ResultResponse<T>  {
    @Schema(description = "요청한 데이터 ")
    private T data;
}
