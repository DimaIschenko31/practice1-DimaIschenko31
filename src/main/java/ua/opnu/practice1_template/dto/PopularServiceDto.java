package ua.opnu.practice1_template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopularServiceDto {
    private Long serviceTypeId;
    private String serviceTypeName;
    private Long count;
}