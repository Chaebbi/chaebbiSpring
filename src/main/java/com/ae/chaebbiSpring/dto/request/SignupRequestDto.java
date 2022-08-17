package com.ae.chaebbiSpring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String name;
    private int age;
    private int gender;
    private String height;
    private String weight;
    private int activity;
}