package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.dto.GamePlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GamePlatformMapper {

    GamePlatformMapper INSTANCE = Mappers.getMapper(GamePlatformMapper.class);

    GamePlatformDto toDto(GamePlatform gameplatform);

    GamePlatformSimpleDto toSimpleDto(GamePlatform gameplatform);

    @InheritInverseConfiguration
    GamePlatform toEntity(GamePlatformDto gameplatformDto);

    List<GamePlatformDto> toDtoList(List<GamePlatform> gameplatformList);

    List<GamePlatform> toEntityList(List<GamePlatformDto> gameplatformDtoList);

}