package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class BistroDto {
    private Long id;
    private String category;
    private String name;

    private String wide;
    private String middle;

    private String rAddr;
    private String lAddr;
    private String tel;
    private String menu;
    private String la;
    private String lo;
    private String url;
}
