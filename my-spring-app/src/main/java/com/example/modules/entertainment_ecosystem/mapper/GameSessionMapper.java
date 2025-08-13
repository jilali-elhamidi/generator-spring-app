package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameSession;
import com.example.modules.entertainment_ecosystem.dto.GameSessionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameSessionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameSessionMapper {

    GameSessionMapper INSTANCE = Mappers.getMapper(GameSessionMapper.class);

    GameSessionDto toDto(GameSession gamesession);

    GameSessionSimpleDto toSimpleDto(GameSession gamesession);

    @InheritInverseConfiguration
    GameSession toEntity(GameSessionDto gamesessionDto);

    List<GameSessionDto> toDtoList(List<GameSession> gamesessionList);

    List<GameSession> toEntityList(List<GameSessionDto> gamesessionDtoList);

}