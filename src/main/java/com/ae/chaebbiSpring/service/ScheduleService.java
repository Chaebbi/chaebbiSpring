package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.Schedule;
import com.ae.chaebbiSpring.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public Long create(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule.getId();
    }
}
