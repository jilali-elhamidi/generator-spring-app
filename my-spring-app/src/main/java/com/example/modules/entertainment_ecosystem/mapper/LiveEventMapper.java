package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.dto.LiveEventDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface LiveEventMapper {

    LiveEventMapper INSTANCE = Mappers.getMapper(LiveEventMapper.class);

    LiveEventDto toDto(LiveEvent liveevent);

    LiveEventSimpleDto toSimpleDto(LiveEvent liveevent);

    @InheritInverseConfiguration
    LiveEvent toEntity(LiveEventDto liveeventDto);

    List<LiveEventDto> toDtoList(List<LiveEvent> liveeventList);

    List<LiveEvent> toEntityList(List<LiveEventDto> liveeventDtoList);

}