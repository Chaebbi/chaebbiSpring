package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Schema(description = "즐겨찾기 삭제 요청 DTO")
@Data
public class BookmarkRequestDto {
    @Schema(description = "즐겨찾기 삭제 하고 싶은 음식점 id ", nullable = false, example = "5")
    @NotNull
    private Long bistroId;
}
