package com.ae.chaebbiSpring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Getter
public class ScheduleRequestDto {
    private String title;
    private String wide;
    private String middle;
    private String sdate;
    private String edate;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    //@DateTimeFormat(pattern = "HH:mm:ss")
    private String stime;
    //@DateTimeFormat(pattern = "HH:mm:ss")
    private String etime;
}
