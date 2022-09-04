package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Schedule;
import com.ae.chaebbiSpring.dto.request.ScheduleRequestDto;
import com.ae.chaebbiSpring.dto.response.CreateScheduleResponseDto;
import com.ae.chaebbiSpring.service.ScheduleService;
import com.ae.chaebbiSpring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.*;
@Api(tags = "Schedule API", description = "일정 관련 API")
@RestController
@RequiredArgsConstructor
public class ScheduleApiController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    //10-1
    @Operation(summary = "[POST] 10-1 일정 등록", description = "일정 등록 API")
    @PostMapping("/api/schedule")
    public BaseResponse<CreateScheduleResponseDto> createSchedule(@AuthenticationPrincipal String userId, @RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        if(scheduleRequestDto.getTitle().isEmpty() || scheduleRequestDto.getTitle().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_TITLE);
        }
        if(scheduleRequestDto.getWide().isEmpty() || scheduleRequestDto.getWide().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_WIDE);
        }
        if(scheduleRequestDto.getMiddle().isEmpty() || scheduleRequestDto.getMiddle().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_MIDDLE);
        }
        if(scheduleRequestDto.getSdate().isEmpty() || scheduleRequestDto.getSdate().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_SDATE);
        }
        if(scheduleRequestDto.getEdate().isEmpty() || scheduleRequestDto.getEdate().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_EDATE);
        }
        if(scheduleRequestDto.getStime().isEmpty() || scheduleRequestDto.getStime().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_STIME);
        }
        if(scheduleRequestDto.getEtime().isEmpty() || scheduleRequestDto.getEtime().equals("")){
            return new BaseResponse<>(PUT_SCHEDULE_ETIME);
        }

        try{
            LocalDate.from(LocalDate.parse(scheduleRequestDto.getSdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LocalDate.from(LocalDate.parse(scheduleRequestDto.getEdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(PUT_SCHEDULE_INVALID_DATE);
        }
        try{
            LocalTime.from(LocalTime.parse(scheduleRequestDto.getStime(), DateTimeFormatter.ofPattern("HH:mm:ss")));
            LocalTime.from(LocalTime.parse(scheduleRequestDto.getEtime(), DateTimeFormatter.ofPattern("HH:mm:ss")));
        }catch (DateTimeParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(PUT_SCHEDULE_INVALID_TIME);
        }


        Schedule schedule = Schedule.createSchedule(userId, scheduleRequestDto);
        Long id = scheduleService.create(schedule);

        return new BaseResponse<>(new CreateScheduleResponseDto(id.intValue()));
    }
}
