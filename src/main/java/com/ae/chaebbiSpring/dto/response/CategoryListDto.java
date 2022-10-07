package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@Schema(description = "대분류, 중분류에 속하는 음식점들의 리스트 DTO")
public class CategoryListDto {
    @Schema(description = "음식점 id")
    private int bistroId;

    @Schema(description = "음식점의 북마크 여부")
    private int isBookmark;
    //@Schema(description = "음식점의 카테고리")
    //private String category;
    @Schema(description = "음식점의 이름")
    @NotNull
    private String name;
    @Schema(description = "음식점의 도로명주소")
    private String roadAddr;
    @Schema(description = "음식점의 지번주소")
    private String lnmAddr;
    @Schema(description = "음식점의 전화번호")
    private String telNo;
}
