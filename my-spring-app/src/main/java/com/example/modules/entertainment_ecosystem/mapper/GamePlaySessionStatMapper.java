package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlaySessionStat;
import com.example.modules.entertainment_ecosystem.dto.GamePlaySessionStatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlaySessionStatSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GamePlaySessionStatMapper {

    GamePlaySessionStatMapper INSTANCE = Mappers.getMapper(GamePlaySessionStatMapper.class);

    GamePlaySessionStatDto toDto(GamePlaySessionStat gameplaysessionstat);

    GamePlaySessionStatSimpleDto toSimpleDto(GamePlaySessionStat gameplaysessionstat);

    @InheritInverseConfiguration
    GamePlaySessionStat toEntity(GamePlaySessionStatDto gameplaysessionstatDto);

    List<GamePlaySessionStatDto> toDtoList(List<GamePlaySessionStat> gameplaysessionstatList);

    List<GamePlaySessionStat> toEntityList(List<GamePlaySessionStatDto> gameplaysessionstatDtoList);

    void updateEntityFromDto(GamePlaySessionStatDto dto, @MappingTarget GamePlaySessionStat entity);

}