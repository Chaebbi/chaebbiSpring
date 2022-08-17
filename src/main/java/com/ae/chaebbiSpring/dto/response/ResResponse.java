package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResResponse<T> {
    private Integer count;
    private T data;
}
