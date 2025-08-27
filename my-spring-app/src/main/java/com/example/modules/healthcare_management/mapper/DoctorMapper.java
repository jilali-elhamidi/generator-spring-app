package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.dto.DoctorDto;
import com.example.modules.healthcare_management.dtosimple.DoctorSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DoctorMapper extends BaseMapper<Doctor, DoctorDto, DoctorSimpleDto> {
}