package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Achievement;
import com.example.modules.entertainment_ecosystem.dto.AchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AchievementSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AchievementMapper {

    AchievementMapper INSTANCE = Mappers.getMapper(AchievementMapper.class);

    AchievementDto toDto(Achievement achievement);

    AchievementSimpleDto toSimpleDto(Achievement achievement);

    @InheritInverseConfiguration
    Achievement toEntity(AchievementDto achievementDto);

    List<AchievementDto> toDtoList(List<Achievement> achievementList);

    List<Achievement> toEntityList(List<AchievementDto> achievementDtoList);

}