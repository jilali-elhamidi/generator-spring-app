package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameAchievement;
import com.example.modules.entertainment_ecosystem.dto.GameAchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameAchievementSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface GameAchievementMapper {

    GameAchievementMapper INSTANCE = Mappers.getMapper(GameAchievementMapper.class);

    GameAchievementDto toDto(GameAchievement gameachievement);

    GameAchievementSimpleDto toSimpleDto(GameAchievement gameachievement);

    @InheritInverseConfiguration
    GameAchievement toEntity(GameAchievementDto gameachievementDto);

    List<GameAchievementDto> toDtoList(List<GameAchievement> gameachievementList);

    List<GameAchievement> toEntityList(List<GameAchievementDto> gameachievementDtoList);

    void updateEntityFromDto(GameAchievementDto dto, @MappingTarget GameAchievement entity);

}