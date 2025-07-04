package ua.opnu.practice1_template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeDto {
    private Long id;
    private String name;
    private BigDecimal standardPrice;
}