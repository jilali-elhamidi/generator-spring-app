package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Notification;
import com.example.modules.entertainment_ecosystem.dto.NotificationDto;
import com.example.modules.entertainment_ecosystem.dtosimple.NotificationSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends BaseMapper<Notification, NotificationDto, NotificationSimpleDto> {
}