package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.dto.GameSessionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameSessionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameSessionMapper extends BaseMapper<GameSession, GameSessionDto, GameSessionSimpleDto> {
}