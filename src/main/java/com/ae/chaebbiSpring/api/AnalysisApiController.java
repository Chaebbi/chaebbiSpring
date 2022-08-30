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

@RestController
@RequiredArgsConstructor
public class AnalysisApiController {
    private final AnalysisService analysisService;
    private final UserService userService;

    //5-1
    @GetMapping("api/analysis")
    public BaseResponse<AnalysisResponseDto> analysisResponse(@AuthenticationPrincipal String userId) {
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;

        List<DateAnalysisDto> findRecords = analysisService.findRecords(Long.valueOf(userId));
        //받아온 기록이 7개일 경우 : 정상로직 : status = 1
        if(findRecords.size() == 7) {
            status = 1;
            List<AnalysisDto> collect = new ArrayList<>();

            for(DateAnalysisDto dateAnalysisDto : findRecords) {
                totalCarb += dateAnalysisDto.getSumCarb();
                totalPro += dateAnalysisDto.getSumPro();
                totalFat += dateAnalysisDto.getSumFat();
                collect.add(new AnalysisDto(dateAnalysisDto.getDate(), dateAnalysisDto.getSumCal().intValue()));
            }
            int sum = totalCarb + totalPro + totalFat;
            ratioCarb = totalCarb * 100 / sum;
            ratioPro = totalPro * 100 / sum;
            ratioFat = totalFat * 100 / sum;
            return new BaseResponse<>(new AnalysisResponseDto(status, ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, collect));
        }
        //비정상 로직 status = 0
        else { return new BaseResponse<>(new AnalysisResponseDto(status,ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, null));}

    }
}
