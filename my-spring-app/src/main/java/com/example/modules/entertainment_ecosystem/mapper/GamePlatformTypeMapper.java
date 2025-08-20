package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.dto.GamePlatformTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GamePlatformTypeMapper {

    GamePlatformTypeMapper INSTANCE = Mappers.getMapper(GamePlatformTypeMapper.class);

    GamePlatformTypeDto toDto(GamePlatformType gameplatformtype);

    GamePlatformTypeSimpleDto toSimpleDto(GamePlatformType gameplatformtype);

    @InheritInverseConfiguration
    GamePlatformType toEntity(GamePlatformTypeDto gameplatformtypeDto);

    List<GamePlatformTypeDto> toDtoList(List<GamePlatformType> gameplatformtypeList);

    List<GamePlatformType> toEntityList(List<GamePlatformTypeDto> gameplatformtypeDtoList);

    void updateEntityFromDto(GamePlatformTypeDto dto, @MappingTarget GamePlatformType entity);

}