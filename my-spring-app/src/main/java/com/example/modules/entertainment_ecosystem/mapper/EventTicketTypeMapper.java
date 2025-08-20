package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.dto.EventTicketTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventTicketTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EventTicketTypeMapper {

    EventTicketTypeMapper INSTANCE = Mappers.getMapper(EventTicketTypeMapper.class);

    EventTicketTypeDto toDto(EventTicketType eventtickettype);

    EventTicketTypeSimpleDto toSimpleDto(EventTicketType eventtickettype);

    @InheritInverseConfiguration
    EventTicketType toEntity(EventTicketTypeDto eventtickettypeDto);

    List<EventTicketTypeDto> toDtoList(List<EventTicketType> eventtickettypeList);

    List<EventTicketType> toEntityList(List<EventTicketTypeDto> eventtickettypeDtoList);

    void updateEntityFromDto(EventTicketTypeDto dto, @MappingTarget EventTicketType entity);

}