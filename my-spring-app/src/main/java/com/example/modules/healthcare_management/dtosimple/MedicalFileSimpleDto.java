package com.example.modules.healthcare_management.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFileSimpleDto {

    private Long id;

    private String fileUrl;

    private String fileType;

}