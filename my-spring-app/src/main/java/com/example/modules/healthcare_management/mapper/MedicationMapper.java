package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.dto.MedicationDto;
import com.example.modules.healthcare_management.dtosimple.MedicationSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MedicationMapper extends BaseMapper<Medication, MedicationDto, MedicationSimpleDto> {
}