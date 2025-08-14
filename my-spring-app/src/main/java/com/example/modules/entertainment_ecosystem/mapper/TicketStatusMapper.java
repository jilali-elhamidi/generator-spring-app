package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TicketStatus;
import com.example.modules.entertainment_ecosystem.dto.TicketStatusDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TicketStatusSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TicketStatusMapper {

    TicketStatusMapper INSTANCE = Mappers.getMapper(TicketStatusMapper.class);

    TicketStatusDto toDto(TicketStatus ticketstatus);

    TicketStatusSimpleDto toSimpleDto(TicketStatus ticketstatus);

    @InheritInverseConfiguration
    TicketStatus toEntity(TicketStatusDto ticketstatusDto);

    List<TicketStatusDto> toDtoList(List<TicketStatus> ticketstatusList);

    List<TicketStatus> toEntityList(List<TicketStatusDto> ticketstatusDtoList);

    void updateEntityFromDto(TicketStatusDto dto, @MappingTarget TicketStatus entity);

}