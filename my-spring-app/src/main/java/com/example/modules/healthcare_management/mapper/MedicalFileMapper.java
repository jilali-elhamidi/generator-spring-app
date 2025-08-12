package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.MedicalFile;
import com.example.modules.healthcare_management.dto.MedicalFileDto;
import com.example.modules.healthcare_management.dtosimple.MedicalFileSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MedicalFileMapper {

    MedicalFileMapper INSTANCE = Mappers.getMapper(MedicalFileMapper.class);

    MedicalFileDto toDto(MedicalFile medicalfile);

    MedicalFileSimpleDto toSimpleDto(MedicalFile medicalfile);

    @InheritInverseConfiguration
    MedicalFile toEntity(MedicalFileDto medicalfileDto);

    List<MedicalFileDto> toDtoList(List<MedicalFile> medicalfileList);

    List<MedicalFile> toEntityList(List<MedicalFileDto> medicalfileDtoList);

}