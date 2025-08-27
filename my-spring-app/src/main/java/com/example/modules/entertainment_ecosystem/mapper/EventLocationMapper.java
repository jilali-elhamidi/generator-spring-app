package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.dto.EventLocationDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventLocationSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EventLocationMapper extends BaseMapper<EventLocation, EventLocationDto, EventLocationSimpleDto> {
}