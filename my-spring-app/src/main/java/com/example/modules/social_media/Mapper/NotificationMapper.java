package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Notification;
import com.example.modules.social_media.dto.NotificationDto;
import com.example.modules.social_media.dtosimple.NotificationSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto toDto(Notification notification);

    NotificationSimpleDto toSimpleDto(Notification notification);

    @InheritInverseConfiguration
    Notification toEntity(NotificationDto notificationDto);

    List<NotificationDto> toDtoList(List<Notification> notificationList);

    List<Notification> toEntityList(List<NotificationDto> notificationDtoList);

}