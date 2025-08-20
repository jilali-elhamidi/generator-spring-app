package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.MedicalRecord;
import com.example.modules.healthcare_management.dto.MedicalRecordDto;
import com.example.modules.healthcare_management.dtosimple.MedicalRecordSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    MedicalRecordMapper INSTANCE = Mappers.getMapper(MedicalRecordMapper.class);

    MedicalRecordDto toDto(MedicalRecord medicalrecord);

    MedicalRecordSimpleDto toSimpleDto(MedicalRecord medicalrecord);

    @InheritInverseConfiguration
    MedicalRecord toEntity(MedicalRecordDto medicalrecordDto);

    List<MedicalRecordDto> toDtoList(List<MedicalRecord> medicalrecordList);

    List<MedicalRecord> toEntityList(List<MedicalRecordDto> medicalrecordDtoList);

    void updateEntityFromDto(MedicalRecordDto dto, @MappingTarget MedicalRecord entity);

}