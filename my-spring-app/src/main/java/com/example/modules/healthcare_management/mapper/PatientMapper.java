package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.dto.PatientDto;
import com.example.modules.healthcare_management.dtosimple.PatientSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PatientMapper extends BaseMapper<Patient, PatientDto, PatientSimpleDto> {
}