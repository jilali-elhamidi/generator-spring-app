package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlaySession;
import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GamePlaySessionMapper {

    GamePlaySessionMapper INSTANCE = Mappers.getMapper(GamePlaySessionMapper.class);

    GamePlaySessionDto toDto(GamePlaySession gameplaysession);

    GamePlaySessionSimpleDto toSimpleDto(GamePlaySession gameplaysession);

    @InheritInverseConfiguration
    GamePlaySession toEntity(GamePlaySessionDto gameplaysessionDto);

    List<GamePlaySessionDto> toDtoList(List<GamePlaySession> gameplaysessionList);

    List<GamePlaySession> toEntityList(List<GamePlaySessionDto> gameplaysessionDtoList);

}