package com.ae.chaebbiSpring.dto.response;

import com.ae.chaebbiSpring.dto.ProblemsDto;
import com.ae.chaebbiSpring.dto.SuggestionsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "식단 분석 response")
@Data
@AllArgsConstructor
public class AnalysisResponseDto {
    @Schema(name = "status", description = "식단분석 정상로직 유무 (1:정상) ", nullable = false, example = "1", type = "int", allowableValues = {"0", "1"})
    @NotNull
    private int status;         //정상 로직이면 1, 비정상이면 0
    @Schema(name = "ratioCarb", description = "7일간 식단의 영양소대비 탄수화물 비율 ", nullable = true, example = "62", type = "int")
    private int ratioCarb;
    @Schema(name = "ratioPro", description = "7일간 식단의 영양소대비  단백질 비율 ", nullable = true, example = "20", type = "int")
    private int ratioPro;
    @Schema(name = "ratioFat", description = "7일간 식단의 영양소대비 지방 비율 ", nullable = true, example = "16", type = "int")
    private int ratioFat;
    @Schema(name = "totalCarb", description = "7일간 식단의 총 섭취 탄수화물", nullable = true, example = "800", type = "int")
    private int totalCarb;
    @Schema(name = "totalPro", description = "7일간 식단의 총 섭취 단백질", nullable = true, example = "264", type = "int")
    private int totalPro;
    @Schema(name = "totalFat", description = "7일간 식단의 총 섭취 지방", nullable = true, example = "216", type = "int")
    private int totalFat;
    @Schema(name ="analysisProblems", description = "사용자의 7일간 문제 식이 리스트", nullable = true)
    private List<ProblemsDto> problemsDtoList;
    @Schema(name = "suggestions", description = "사용자 문제 식단에 대한 제안 음식리스트", nullable = true )
    private List<SuggestionsDto> suggestionsDtoList;
    @Schema(name = "analysisDtos", description = "날짜별 총 섭취 칼로리", nullable = true)
    private List<AnalysisDto> analysisDtos;
}
