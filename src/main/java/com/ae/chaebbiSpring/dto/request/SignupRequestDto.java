package com.ae.chaebbiSpring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String name;
    private String age;
    private String gender;
    private String height;
    private String weight;
    private String activity;
}