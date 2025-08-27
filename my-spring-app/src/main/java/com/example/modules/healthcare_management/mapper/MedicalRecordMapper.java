package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.dto.MedicalRecordDto;
import com.example.modules.healthcare_management.dtosimple.MedicalRecordSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MedicalRecordMapper extends BaseMapper<MedicalRecord, MedicalRecordDto, MedicalRecordSimpleDto> {
}