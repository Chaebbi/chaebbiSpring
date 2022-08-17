package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.dto.DateAnalysisDto;
import com.ae.chaebbiSpring.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {
    private final RecordRepository recordRepository;

    public List<DateAnalysisDto> findRecords(Long id) {
        return recordRepository.analysisDate(id);
    }
}
