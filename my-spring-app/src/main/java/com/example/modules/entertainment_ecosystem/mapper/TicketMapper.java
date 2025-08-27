package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.dto.TicketDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TicketMapper extends BaseMapper<Ticket, TicketDto, TicketSimpleDto> {
}