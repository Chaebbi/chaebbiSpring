package com.ae.chaebbiSpring.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class CategoryRequestDto {
    @NotNull
    private String wide;
    @NotNull
    private String middle;
}
