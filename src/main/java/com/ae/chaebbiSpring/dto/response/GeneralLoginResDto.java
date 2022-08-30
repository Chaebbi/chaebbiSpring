package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GeneralLoginResDto {
    @NotNull
    private String status;
    @NotNull
    private Long userId;
    @NotNull
    private String token;
}
