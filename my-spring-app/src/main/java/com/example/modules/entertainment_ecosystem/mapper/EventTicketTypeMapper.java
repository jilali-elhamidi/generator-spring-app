package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventTicketType;
import com.example.modules.entertainment_ecosystem.dto.EventTicketTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventTicketTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EventTicketTypeMapper extends BaseMapper<EventTicketType, EventTicketTypeDto, EventTicketTypeSimpleDto> {
}