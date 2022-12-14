package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class IngredientResponseDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
}
