package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Prescription;
import com.example.modules.healthcare_management.dto.PrescriptionDto;
import com.example.modules.healthcare_management.dtosimple.PrescriptionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    PrescriptionMapper INSTANCE = Mappers.getMapper(PrescriptionMapper.class);

    PrescriptionDto toDto(Prescription prescription);

    PrescriptionSimpleDto toSimpleDto(Prescription prescription);

    @InheritInverseConfiguration
    Prescription toEntity(PrescriptionDto prescriptionDto);

    List<PrescriptionDto> toDtoList(List<Prescription> prescriptionList);

    List<Prescription> toEntityList(List<PrescriptionDto> prescriptionDtoList);

}