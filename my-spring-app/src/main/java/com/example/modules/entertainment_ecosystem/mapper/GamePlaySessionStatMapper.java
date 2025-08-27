package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlaySessionStat;
import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionStatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionStatSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GamePlaySessionStatMapper extends BaseMapper<GamePlaySessionStat, GamePlaySessionStatDto, GamePlaySessionStatSimpleDto> {
}