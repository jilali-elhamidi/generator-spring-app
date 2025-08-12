package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.dto.EventTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EventTypeMapper {

    EventTypeMapper INSTANCE = Mappers.getMapper(EventTypeMapper.class);

    EventTypeDto toDto(EventType eventtype);

    EventTypeSimpleDto toSimpleDto(EventType eventtype);

    @InheritInverseConfiguration
    EventType toEntity(EventTypeDto eventtypeDto);

    List<EventTypeDto> toDtoList(List<EventType> eventtypeList);

    List<EventType> toEntityList(List<EventTypeDto> eventtypeDtoList);

}