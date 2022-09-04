package com.ae.chaebbiSpring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "식단 조회 request")
@Data
public class DateRecordRequestDto {
    @Pattern(regexp = "\\d{4}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[01]).")
    @Schema(description = "조회하고 싶은 날짜", example = "2022.09.04.", nullable = false, type = "String")
    @NotNull
    private String date;
}
