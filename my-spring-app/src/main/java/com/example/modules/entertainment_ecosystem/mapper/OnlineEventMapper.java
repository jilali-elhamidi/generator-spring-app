package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.dto.OnlineEventDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface OnlineEventMapper {

    OnlineEventMapper INSTANCE = Mappers.getMapper(OnlineEventMapper.class);

    OnlineEventDto toDto(OnlineEvent onlineevent);

    OnlineEventSimpleDto toSimpleDto(OnlineEvent onlineevent);

    @InheritInverseConfiguration
    OnlineEvent toEntity(OnlineEventDto onlineeventDto);

    List<OnlineEventDto> toDtoList(List<OnlineEvent> onlineeventList);

    List<OnlineEvent> toEntityList(List<OnlineEventDto> onlineeventDtoList);

}