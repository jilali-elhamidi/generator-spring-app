package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.NotificationType;
import com.example.modules.entertainment_ecosystem.dto.NotificationTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.NotificationTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface NotificationTypeMapper extends BaseMapper<NotificationType, NotificationTypeDto, NotificationTypeSimpleDto> {
}