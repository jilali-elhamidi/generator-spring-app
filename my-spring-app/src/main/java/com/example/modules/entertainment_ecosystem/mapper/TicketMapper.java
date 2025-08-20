package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Ticket;
import com.example.modules.entertainment_ecosystem.dto.TicketDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketDto toDto(Ticket ticket);

    TicketSimpleDto toSimpleDto(Ticket ticket);

    @InheritInverseConfiguration
    Ticket toEntity(TicketDto ticketDto);

    List<TicketDto> toDtoList(List<Ticket> ticketList);

    List<Ticket> toEntityList(List<TicketDto> ticketDtoList);

    void updateEntityFromDto(TicketDto dto, @MappingTarget Ticket entity);

}