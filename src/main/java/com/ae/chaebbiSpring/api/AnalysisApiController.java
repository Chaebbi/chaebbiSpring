package com.ae.chaebbiSpring.api;

import com.ae.chaebbiSpring.config.BaseResponse;
import com.ae.chaebbiSpring.domain.Record;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.dto.response.AnalysisDto;
import com.ae.chaebbiSpring.dto.response.AnalysisResponseDto;
import com.ae.chaebbiSpring.dto.DateAnalysisDto;
import com.ae.chaebbiSpring.service.AnalysisService;
import com.ae.chaebbiSpring.service.RecordService;
import com.ae.chaebbiSpring.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.ae.chaebbiSpring.config.BaseResponseStatus.EMPTY_JWT;
import static com.ae.chaebbiSpring.config.BaseResponseStatus.INVALID_JWT;
import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;

@Api(tags = "Analysis API", description = "분석 API")
@RestController
@RequiredArgsConstructor
public class AnalysisApiController {
    private final AnalysisService analysisService;
    private final UserService userService;

    //5-1
    @Operation(summary = "식단 분석", description = "식단 분석 API")
    @GetMapping("api/analysis")
    public BaseResponse<AnalysisResponseDto> analysisResponse(@AuthenticationPrincipal String userId) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        List<DateAnalysisDto> findWeekRecords = analysisService.findRecords(Long.valueOf(userId));
        return new BaseResponse<>(analysisService.weekAnalysis(findWeekRecords, Long.valueOf(userId)));

    }


}
