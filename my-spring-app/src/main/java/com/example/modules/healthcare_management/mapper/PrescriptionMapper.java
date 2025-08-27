package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.dto.PrescriptionDto;
import com.example.modules.healthcare_management.dtosimple.PrescriptionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PrescriptionMapper extends BaseMapper<Prescription, PrescriptionDto, PrescriptionSimpleDto> {
}