package com.ae.chaebbiSpring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse<T>  {
    private T data;
}
