package ua.opnu.practice1_template.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRecordDto {
    private Long id;
    private Long carId;
    private Long mechanicId;
    private LocalDate date;
    private String description;
    private List<Long> serviceTypeIds = new ArrayList<>();
}