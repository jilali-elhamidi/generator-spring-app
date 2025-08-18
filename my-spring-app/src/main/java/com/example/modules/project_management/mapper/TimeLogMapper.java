package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.TimeLog;
import com.example.modules.project_management.dto.TimeLogDto;
import com.example.modules.project_management.dtosimple.TimeLogSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TimeLogMapper {

    TimeLogMapper INSTANCE = Mappers.getMapper(TimeLogMapper.class);

    TimeLogDto toDto(TimeLog timelog);

    TimeLogSimpleDto toSimpleDto(TimeLog timelog);

    @InheritInverseConfiguration
    TimeLog toEntity(TimeLogDto timelogDto);

    List<TimeLogDto> toDtoList(List<TimeLog> timelogList);

    List<TimeLog> toEntityList(List<TimeLogDto> timelogDtoList);

    void updateEntityFromDto(TimeLogDto dto, @MappingTarget TimeLog entity);

}