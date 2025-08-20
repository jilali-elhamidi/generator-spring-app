package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.dto.AppointmentDto;
import com.example.modules.healthcare_management.dtosimple.AppointmentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentDto toDto(Appointment appointment);

    AppointmentSimpleDto toSimpleDto(Appointment appointment);

    @InheritInverseConfiguration
    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDtoList(List<Appointment> appointmentList);

    List<Appointment> toEntityList(List<AppointmentDto> appointmentDtoList);

    void updateEntityFromDto(AppointmentDto dto, @MappingTarget Appointment entity);

}