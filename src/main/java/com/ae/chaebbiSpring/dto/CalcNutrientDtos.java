package com.ae.chaebbiSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CalcNutrientDtos {
    private String rcal;
    private String rcarb;
    private String rpro;
    private String rfat;
}
