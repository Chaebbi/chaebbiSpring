package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@Schema(description = "즐겨찾기 등록 응답 DTO")
public class CreateBookmarkResponseDto {
    @Schema(description = "등록된 북마크 id ", nullable = false, example = "5")
    @NotNull
    private int id;
}
