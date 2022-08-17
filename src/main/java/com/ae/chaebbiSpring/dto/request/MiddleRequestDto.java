package com.ae.chaebbiSpring.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MiddleRequestDto {
    @NotNull
    private String wide;
}
