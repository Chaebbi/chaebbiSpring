package com.ae.chaebbiSpring.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ScheduleRequestDto {
    private String title;
    private String wide;
    private String middle;
    private String sDate;
    private String eDate;
    private String sTime;
    private String eTime;
}
