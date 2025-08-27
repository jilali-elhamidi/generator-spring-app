package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.dto.EventSponsorshipDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventSponsorshipSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EventSponsorshipMapper extends BaseMapper<EventSponsorship, EventSponsorshipDto, EventSponsorshipSimpleDto> {
}