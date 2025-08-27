package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.dto.MedicalFileDto;
import com.example.modules.healthcare_management.dtosimple.MedicalFileSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MedicalFileMapper extends BaseMapper<MedicalFile, MedicalFileDto, MedicalFileSimpleDto> {
}