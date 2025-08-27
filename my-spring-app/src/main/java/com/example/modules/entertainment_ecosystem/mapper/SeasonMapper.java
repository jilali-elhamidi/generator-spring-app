package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.dto.SeasonDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SeasonSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SeasonMapper extends BaseMapper<Season, SeasonDto, SeasonSimpleDto> {
}