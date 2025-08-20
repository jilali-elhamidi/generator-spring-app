package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumModerator;
import com.example.modules.entertainment_ecosystem.dto.ForumModeratorDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumModeratorSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ForumModeratorMapper {

    ForumModeratorMapper INSTANCE = Mappers.getMapper(ForumModeratorMapper.class);

    ForumModeratorDto toDto(ForumModerator forummoderator);

    ForumModeratorSimpleDto toSimpleDto(ForumModerator forummoderator);

    @InheritInverseConfiguration
    ForumModerator toEntity(ForumModeratorDto forummoderatorDto);

    List<ForumModeratorDto> toDtoList(List<ForumModerator> forummoderatorList);

    List<ForumModerator> toEntityList(List<ForumModeratorDto> forummoderatorDtoList);

    void updateEntityFromDto(ForumModeratorDto dto, @MappingTarget ForumModerator entity);

}