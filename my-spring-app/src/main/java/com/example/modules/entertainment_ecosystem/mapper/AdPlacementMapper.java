package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.dto.AdPlacementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AdPlacementSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AdPlacementMapper extends BaseMapper<AdPlacement, AdPlacementDto, AdPlacementSimpleDto> {
}