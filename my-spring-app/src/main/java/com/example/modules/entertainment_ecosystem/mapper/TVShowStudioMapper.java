package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TVShowStudio;
import com.example.modules.entertainment_ecosystem.dto.TVShowStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowStudioSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TVShowStudioMapper extends BaseMapper<TVShowStudio, TVShowStudioDto, TVShowStudioSimpleDto> {
}