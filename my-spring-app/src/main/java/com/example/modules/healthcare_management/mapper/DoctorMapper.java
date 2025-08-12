package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Doctor;
import com.example.modules.healthcare_management.dto.DoctorDto;
import com.example.modules.healthcare_management.dtosimple.DoctorSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    DoctorDto toDto(Doctor doctor);

    DoctorSimpleDto toSimpleDto(Doctor doctor);

    @InheritInverseConfiguration
    Doctor toEntity(DoctorDto doctorDto);

    List<DoctorDto> toDtoList(List<Doctor> doctorList);

    List<Doctor> toEntityList(List<DoctorDto> doctorDtoList);

}