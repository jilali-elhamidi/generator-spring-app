package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.dto.NotificationTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.NotificationTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface NotificationTypeMapper {

    NotificationTypeMapper INSTANCE = Mappers.getMapper(NotificationTypeMapper.class);

    NotificationTypeDto toDto(NotificationType notificationtype);

    NotificationTypeSimpleDto toSimpleDto(NotificationType notificationtype);

    @InheritInverseConfiguration
    NotificationType toEntity(NotificationTypeDto notificationtypeDto);

    List<NotificationTypeDto> toDtoList(List<NotificationType> notificationtypeList);

    List<NotificationType> toEntityList(List<NotificationTypeDto> notificationtypeDtoList);

    void updateEntityFromDto(NotificationTypeDto dto, @MappingTarget NotificationType entity);

}