package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.dto.EventLocationDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventLocationSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EventLocationMapper {

    EventLocationMapper INSTANCE = Mappers.getMapper(EventLocationMapper.class);

    EventLocationDto toDto(EventLocation eventlocation);

    EventLocationSimpleDto toSimpleDto(EventLocation eventlocation);

    @InheritInverseConfiguration
    EventLocation toEntity(EventLocationDto eventlocationDto);

    List<EventLocationDto> toDtoList(List<EventLocation> eventlocationList);

    List<EventLocation> toEntityList(List<EventLocationDto> eventlocationDtoList);

    void updateEntityFromDto(EventLocationDto dto, @MappingTarget EventLocation entity);

}