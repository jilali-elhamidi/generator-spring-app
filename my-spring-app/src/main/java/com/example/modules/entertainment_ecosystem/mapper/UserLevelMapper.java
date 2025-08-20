package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserLevel;
import com.example.modules.entertainment_ecosystem.dto.UserLevelDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserLevelSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserLevelMapper {

    UserLevelMapper INSTANCE = Mappers.getMapper(UserLevelMapper.class);

    UserLevelDto toDto(UserLevel userlevel);

    UserLevelSimpleDto toSimpleDto(UserLevel userlevel);

    @InheritInverseConfiguration
    UserLevel toEntity(UserLevelDto userlevelDto);

    List<UserLevelDto> toDtoList(List<UserLevel> userlevelList);

    List<UserLevel> toEntityList(List<UserLevelDto> userlevelDtoList);

    void updateEntityFromDto(UserLevelDto dto, @MappingTarget UserLevel entity);

}