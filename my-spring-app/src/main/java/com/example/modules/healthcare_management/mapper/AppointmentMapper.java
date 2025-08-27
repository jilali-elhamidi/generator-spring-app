package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.dto.AppointmentDto;
import com.example.modules.healthcare_management.dtosimple.AppointmentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper extends BaseMapper<Appointment, AppointmentDto, AppointmentSimpleDto> {
}