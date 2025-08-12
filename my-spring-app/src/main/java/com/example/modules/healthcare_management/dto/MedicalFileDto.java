package com.example.modules.healthcare_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.healthcare_management.dtosimple.MedicalRecordSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFileDto {

    private Long id;

    private String fileUrl;

    private String fileType;

    private MedicalRecordSimpleDto record;

}