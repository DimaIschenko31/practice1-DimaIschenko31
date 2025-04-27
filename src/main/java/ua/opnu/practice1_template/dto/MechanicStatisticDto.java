package ua.opnu.practice1_template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MechanicStatisticDto {
    private Long mechanicId;
    private String mechanicName;
    private Long servicesCount;
}