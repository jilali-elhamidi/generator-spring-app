package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.dto.SponsorDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SponsorSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SponsorMapper extends BaseMapper<Sponsor, SponsorDto, SponsorSimpleDto> {
}