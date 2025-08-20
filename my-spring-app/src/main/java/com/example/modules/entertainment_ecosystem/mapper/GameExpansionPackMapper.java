package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.dto.GameExpansionPackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameExpansionPackSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameExpansionPackMapper {

    GameExpansionPackMapper INSTANCE = Mappers.getMapper(GameExpansionPackMapper.class);

    GameExpansionPackDto toDto(GameExpansionPack gameexpansionpack);

    GameExpansionPackSimpleDto toSimpleDto(GameExpansionPack gameexpansionpack);

    @InheritInverseConfiguration
    GameExpansionPack toEntity(GameExpansionPackDto gameexpansionpackDto);

    List<GameExpansionPackDto> toDtoList(List<GameExpansionPack> gameexpansionpackList);

    List<GameExpansionPack> toEntityList(List<GameExpansionPackDto> gameexpansionpackDtoList);

    void updateEntityFromDto(GameExpansionPackDto dto, @MappingTarget GameExpansionPack entity);

}