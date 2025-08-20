package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Patient;
import com.example.modules.healthcare_management.dto.PatientDto;
import com.example.modules.healthcare_management.dtosimple.PatientSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientDto toDto(Patient patient);

    PatientSimpleDto toSimpleDto(Patient patient);

    @InheritInverseConfiguration
    Patient toEntity(PatientDto patientDto);

    List<PatientDto> toDtoList(List<Patient> patientList);

    List<Patient> toEntityList(List<PatientDto> patientDtoList);

    void updateEntityFromDto(PatientDto dto, @MappingTarget Patient entity);

}