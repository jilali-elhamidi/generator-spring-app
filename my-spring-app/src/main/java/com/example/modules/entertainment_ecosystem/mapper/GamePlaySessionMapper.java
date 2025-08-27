package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GamePlaySessionMapper extends BaseMapper<GamePlaySession, GamePlaySessionDto, GamePlaySessionSimpleDto> {
}