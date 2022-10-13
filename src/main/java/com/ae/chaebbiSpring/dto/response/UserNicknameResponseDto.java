package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNicknameResponseDto {
    private boolean isExist;
    private String note;
}
