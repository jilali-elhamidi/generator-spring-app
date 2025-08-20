package com.example.modules.healthcare_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordSimpleDto {

    private Long id;

    private Date recordDate;

    private String diagnosis;

    private String notes;

}