package com.ae.chaebbiSpring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GeneralLoginReqDto {
    @NotNull
    private String email;
    @NotNull
    private String pwd;
}
