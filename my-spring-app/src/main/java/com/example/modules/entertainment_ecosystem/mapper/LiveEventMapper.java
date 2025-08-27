package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.LiveEvent;
import com.example.modules.entertainment_ecosystem.dto.LiveEventDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveEventSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface LiveEventMapper extends BaseMapper<LiveEvent, LiveEventDto, LiveEventSimpleDto> {
}