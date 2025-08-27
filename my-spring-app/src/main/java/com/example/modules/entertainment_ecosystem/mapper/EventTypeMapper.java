package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventType;
import com.example.modules.entertainment_ecosystem.dto.EventTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EventTypeMapper extends BaseMapper<EventType, EventTypeDto, EventTypeSimpleDto> {
}