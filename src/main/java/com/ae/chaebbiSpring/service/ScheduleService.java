package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.Schedule;
import com.ae.chaebbiSpring.dto.response.ScheduleDto;
import com.ae.chaebbiSpring.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Long create(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule.getId();
    }


    public List<ScheduleDto> findschedule(String userId, String todayDate) {
        List<Schedule> schedules =  scheduleRepository.findschedule(Integer.valueOf(userId), todayDate);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for(Schedule schedule : schedules){
            scheduleDtos.add(new ScheduleDto(schedule.getId(), schedule.getSdate(), schedule.getTitle(), schedule.getStime()));
        }
        return scheduleDtos;
    }
}
