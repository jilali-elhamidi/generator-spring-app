package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Notification;
import com.example.modules.social_media.dto.NotificationDto;
import com.example.modules.social_media.dtosimple.NotificationSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends BaseMapper<Notification, NotificationDto, NotificationSimpleDto> {
}