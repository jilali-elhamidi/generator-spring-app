package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserAchievement;
import com.example.modules.entertainment_ecosystem.dto.UserAchievementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserAchievementSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserAchievementMapper {

    UserAchievementMapper INSTANCE = Mappers.getMapper(UserAchievementMapper.class);

    UserAchievementDto toDto(UserAchievement userachievement);

    UserAchievementSimpleDto toSimpleDto(UserAchievement userachievement);

    @InheritInverseConfiguration
    UserAchievement toEntity(UserAchievementDto userachievementDto);

    List<UserAchievementDto> toDtoList(List<UserAchievement> userachievementList);

    List<UserAchievement> toEntityList(List<UserAchievementDto> userachievementDtoList);

    void updateEntityFromDto(UserAchievementDto dto, @MappingTarget UserAchievement entity);

}