package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Medication;
import com.example.modules.healthcare_management.dto.MedicationDto;
import com.example.modules.healthcare_management.dtosimple.MedicationSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MedicationMapper {

    MedicationMapper INSTANCE = Mappers.getMapper(MedicationMapper.class);

    MedicationDto toDto(Medication medication);

    MedicationSimpleDto toSimpleDto(Medication medication);

    @InheritInverseConfiguration
    Medication toEntity(MedicationDto medicationDto);

    List<MedicationDto> toDtoList(List<Medication> medicationList);

    List<Medication> toEntityList(List<MedicationDto> medicationDtoList);

}