package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.dto.ConcertVenueDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ConcertVenueSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ConcertVenueMapper {

    ConcertVenueMapper INSTANCE = Mappers.getMapper(ConcertVenueMapper.class);

    ConcertVenueDto toDto(ConcertVenue concertvenue);

    ConcertVenueSimpleDto toSimpleDto(ConcertVenue concertvenue);

    @InheritInverseConfiguration
    ConcertVenue toEntity(ConcertVenueDto concertvenueDto);

    List<ConcertVenueDto> toDtoList(List<ConcertVenue> concertvenueList);

    List<ConcertVenue> toEntityList(List<ConcertVenueDto> concertvenueDtoList);

    void updateEntityFromDto(ConcertVenueDto dto, @MappingTarget ConcertVenue entity);

}