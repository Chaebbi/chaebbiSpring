package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.domain.Schedule;
import com.ae.chaebbiSpring.dto.request.ScheduleRequestDto;
import com.ae.chaebbiSpring.dto.response.CreateScheduleResponseDto;
import com.ae.chaebbiSpring.service.ScheduleService;
import com.ae.chaebbiSpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ScheduleApiController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    @PostMapping("/api/schedule")
    public CreateScheduleResponseDto createSchedule(@AuthenticationPrincipal String userId, @RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = Schedule.createSchedule(userId, scheduleRequestDto);
        Long id = scheduleService.create(schedule);

        return new CreateScheduleResponseDto(id.intValue());
    }
}
