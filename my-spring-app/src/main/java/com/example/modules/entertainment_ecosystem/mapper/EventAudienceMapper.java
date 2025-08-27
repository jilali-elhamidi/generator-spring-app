package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.dto.EventAudienceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventAudienceSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EventAudienceMapper extends BaseMapper<EventAudience, EventAudienceDto, EventAudienceSimpleDto> {
}