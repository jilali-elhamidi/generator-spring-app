package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import com.example.modules.entertainment_ecosystem.dto.OnlineEventDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface OnlineEventMapper extends BaseMapper<OnlineEvent, OnlineEventDto, OnlineEventSimpleDto> {
}