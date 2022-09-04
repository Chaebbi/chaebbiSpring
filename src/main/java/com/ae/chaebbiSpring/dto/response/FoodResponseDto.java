package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "음식 인덱스 조회 response")
@Data
@AllArgsConstructor
public class FoodResponseDto {
    @Schema(name = "name", description = "조회된 음식의 이름", nullable = false, example = "만두국", type = "String")
    @NotNull
    private String name;
    @Schema(name = "capacity", description = "조회된 음식의 용량(g) ", nullable = true, example = "700", type = "int")
    private int capacity;
    @Schema(name = "calory", description = "조회된 음식의 열량(kcal)", nullable = true, example = "434.05", type = "Double")
    private double calory;
    @Schema(name = "carb", description = "조회된 음식의 탄수화물", nullable = true, example = "47.832", type = "Double")
    private double carb;
    @Schema(name = "pro", description = "조회된 음식의 단백질", nullable = true, example = "21.429", type = "Double")
    private double pro;
    @Schema(name = "fat", description = "조회된 음식의 지방", nullable = true, example = "17.445", type = "Double")
    private double fat;
}
