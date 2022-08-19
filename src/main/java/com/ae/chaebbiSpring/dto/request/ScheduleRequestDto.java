package com.ae.chaebbiSpring.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ScheduleRequestDto {
    private String title;
    private String wide;
    private String middle;
    private String sdate;
    private String edate;
    private String stime;
    private String etime;
}
