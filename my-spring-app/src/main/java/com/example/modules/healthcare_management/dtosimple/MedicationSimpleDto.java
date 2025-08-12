package com.example.modules.healthcare_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationSimpleDto {
    private Long id;

    private String name;

    private String type;

    private String manufacturer;

}