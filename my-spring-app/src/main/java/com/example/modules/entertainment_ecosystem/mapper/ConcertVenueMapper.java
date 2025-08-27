package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ConcertVenue;
import com.example.modules.entertainment_ecosystem.dto.ConcertVenueDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ConcertVenueSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ConcertVenueMapper extends BaseMapper<ConcertVenue, ConcertVenueDto, ConcertVenueSimpleDto> {
}