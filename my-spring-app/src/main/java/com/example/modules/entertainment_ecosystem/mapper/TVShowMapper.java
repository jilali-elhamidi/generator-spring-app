package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.dto.TVShowDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TVShowMapper extends BaseMapper<TVShow, TVShowDto, TVShowSimpleDto> {
}