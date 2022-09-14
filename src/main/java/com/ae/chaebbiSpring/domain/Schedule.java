package com.ae.chaebbiSpring.domain;

import com.ae.chaebbiSpring.dto.request.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    @Id
    @Column(name="scheduleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String wide;
    private String middle;

    @Column(name = "start_date")
    private String sdate;
    @Column(name = "end_date")
    private String edate;
    @Column(name = "start_time")
    private String stime;
    @Column(name = "end_time")
    private String etime;

    @Column(name ="user_id")
    private int uId;

    public static Schedule createSchedule(String userId, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleRequestDto.getTitle());
        schedule.setWide(scheduleRequestDto.getWide());
        schedule.setMiddle(scheduleRequestDto.getMiddle());
        schedule.setSdate(scheduleRequestDto.getSdate());
        schedule.setEdate(scheduleRequestDto.getEdate());
        schedule.setStime(scheduleRequestDto.getStime());
        schedule.setEtime(scheduleRequestDto.getEtime());
        schedule.setUId(Integer.parseInt(userId));
        return schedule;
    }
}
