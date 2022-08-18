package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailRecordResponseDto {
    private String name;
    private String text;
    private String cal;
    private String carb;
    private String protein;
    private String fat;
    private String image_url;
    private String date;
    private String time;
    private Double amount;
}
