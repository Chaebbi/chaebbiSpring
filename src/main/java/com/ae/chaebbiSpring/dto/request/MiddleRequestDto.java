package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "음식점 중분류 조회 요청 DTO")
public class MiddleRequestDto {
    @Schema(description = "중분류 조회할 대분류 ", nullable = false, example = "서울특별시")
    @NotNull
    private String wide;
}
