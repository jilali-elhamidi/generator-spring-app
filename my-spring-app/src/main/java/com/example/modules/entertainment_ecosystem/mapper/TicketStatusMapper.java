package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.dto.TicketStatusDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketStatusSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TicketStatusMapper extends BaseMapper<TicketStatus, TicketStatusDto, TicketStatusSimpleDto> {
}