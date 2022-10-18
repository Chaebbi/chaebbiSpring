package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.dto.DateAnalysisDto;
import com.ae.chaebbiSpring.dto.response.AnalysisDto;
import com.ae.chaebbiSpring.dto.response.AnalysisResponseDto;
import com.ae.chaebbiSpring.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {
    private final RecordRepository recordRepository;

    public List<DateAnalysisDto> findRecords(Long id) {
        return recordRepository.analysisDate(id);
    }
    public AnalysisResponseDto weekAnalysis(List<DateAnalysisDto> weekRecords) {
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;
        //받아온 기록이 7개일 경우 : 정상로직 : status = 1
        if(weekRecords.size() == 7) {
            // 식단에 문제가 있는지 확인

            status = 1;
            List<AnalysisDto> collect = new ArrayList<>();

            for(DateAnalysisDto dateAnalysisDto : weekRecords) {
                totalCarb += dateAnalysisDto.getSumCarb();
                totalPro += dateAnalysisDto.getSumPro();
                totalFat += dateAnalysisDto.getSumFat();
                collect.add(new AnalysisDto(dateAnalysisDto.getDate(), dateAnalysisDto.getSumCal().intValue()));
            }
            int sum = totalCarb + totalPro + totalFat;
            ratioCarb = totalCarb * 100 / sum;
            ratioPro = totalPro * 100 / sum;
            ratioFat = totalFat * 100 / sum;
            return new AnalysisResponseDto(status, ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, collect);
        }
        //비정상 로직 status = 0
        else { return new AnalysisResponseDto(status,ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, null);}




    }
}
