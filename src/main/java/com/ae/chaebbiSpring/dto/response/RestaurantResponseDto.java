package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@Schema(description = "즐겨찾기 북마크 조회 응답 DTO")
public class RestaurantResponseDto {
    @Schema(description = "음식점 id")
    private Long bistroId;
    @Schema(description = "음식점의 이름")
    @NotNull
    private String name;
    @Schema(description = "음식점의 도로명주소")
    private String roadAddr;
    @Schema(description = "음식점의 지번주소")
    private String lnmAddr;
    @Schema(description = "음식점의 전화번호")
    private String telNo;
    @Schema(description = "음식점의 위도")
    @NotNull
    private String la;
    @Schema(description = "음식점의 경도")
    @NotNull
    private String lo;
    @Schema(description = "음식점의 네이버지도 주소")
    private String bistroUrl;
}
