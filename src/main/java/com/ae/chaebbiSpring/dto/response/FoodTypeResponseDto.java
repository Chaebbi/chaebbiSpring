package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "모든 음식 조회 response")
@Data
@AllArgsConstructor
public class FoodTypeResponseDto {
    @Schema(name = "id", description = "조회된 식단의 id", nullable = false, example = "1", type = "Long")
    @NotNull
    private Long id;
    @Schema(name = "name", description = "조회된 식단의 이름", nullable = false, example = "더덕구이", type = "String")
    @NotNull
    private String name;
}
