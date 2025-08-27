package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.TVShowMerchandise;
import com.example.modules.entertainment_ecosystem.dto.TVShowMerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TVShowMerchandiseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TVShowMerchandiseMapper extends BaseMapper<TVShowMerchandise, TVShowMerchandiseDto, TVShowMerchandiseSimpleDto> {
}