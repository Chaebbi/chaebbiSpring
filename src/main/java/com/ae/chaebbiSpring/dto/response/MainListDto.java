package com.ae.chaebbiSpring.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class MainListDto {
    private int bistroId;

    private int isBookmark;
    @NotNull
    private String name;
    private String roadAddr;
    private String lnmAddr;
    private String telNo;
    private String menu;
    private String la;
    private String lo;
}
