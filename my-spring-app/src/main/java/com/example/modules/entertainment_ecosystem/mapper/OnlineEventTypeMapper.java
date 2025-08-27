package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.OnlineEventType;
import com.example.modules.entertainment_ecosystem.dto.OnlineEventTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface OnlineEventTypeMapper extends BaseMapper<OnlineEventType, OnlineEventTypeDto, OnlineEventTypeSimpleDto> {
}