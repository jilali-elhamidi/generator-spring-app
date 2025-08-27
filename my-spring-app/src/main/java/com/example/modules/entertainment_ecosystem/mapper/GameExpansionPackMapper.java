package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.dto.GameExpansionPackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameExpansionPackSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameExpansionPackMapper extends BaseMapper<GameExpansionPack, GameExpansionPackDto, GameExpansionPackSimpleDto> {
}