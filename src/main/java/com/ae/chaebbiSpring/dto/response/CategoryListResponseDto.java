package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@AllArgsConstructor
@Schema(description = "음식점 대분류, 중분류별 조회 응답 DTO")
public class CategoryListResponseDto {
    @Schema(description = "요청한 음식점의 카테고리 리스트 ")
    @NotNull
    private List<String> categories;
    @Schema(description = "대분류, 중분류에 속하는 음식점의 개수 ")
    @NotNull
    private int size;
    @Schema(description = "대분류, 중분류에 속하는 음식점들의 리스트 ")
    @NotNull
    private List<CategoryListDto> CategoryList;
}
