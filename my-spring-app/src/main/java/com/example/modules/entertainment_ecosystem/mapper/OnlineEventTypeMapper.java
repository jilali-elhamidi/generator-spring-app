package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.dto.OnlineEventTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface OnlineEventTypeMapper {

    OnlineEventTypeMapper INSTANCE = Mappers.getMapper(OnlineEventTypeMapper.class);

    OnlineEventTypeDto toDto(OnlineEventType onlineeventtype);

    OnlineEventTypeSimpleDto toSimpleDto(OnlineEventType onlineeventtype);

    @InheritInverseConfiguration
    OnlineEventType toEntity(OnlineEventTypeDto onlineeventtypeDto);

    List<OnlineEventTypeDto> toDtoList(List<OnlineEventType> onlineeventtypeList);

    List<OnlineEventType> toEntityList(List<OnlineEventTypeDto> onlineeventtypeDtoList);

    void updateEntityFromDto(OnlineEventTypeDto dto, @MappingTarget OnlineEventType entity);

}