package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@Schema(description = "음식점 대분류, 중분류별 조회 요청 DTO")
public class CategoryRequestDto {
    @Schema(description = "음식점 요청할 대분류 ",  example = "서울특별시")
    @NotNull
    private String wide;
    @Schema(description = "음식점 요청할 중분류 ",  example = "강남구")
    @NotNull
    private String middle;
}
