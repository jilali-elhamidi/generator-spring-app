package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.dto.EventSponsorshipDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventSponsorshipSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EventSponsorshipMapper {

    EventSponsorshipMapper INSTANCE = Mappers.getMapper(EventSponsorshipMapper.class);

    EventSponsorshipDto toDto(EventSponsorship eventsponsorship);

    EventSponsorshipSimpleDto toSimpleDto(EventSponsorship eventsponsorship);

    @InheritInverseConfiguration
    EventSponsorship toEntity(EventSponsorshipDto eventsponsorshipDto);

    List<EventSponsorshipDto> toDtoList(List<EventSponsorship> eventsponsorshipList);

    List<EventSponsorship> toEntityList(List<EventSponsorshipDto> eventsponsorshipDtoList);

}