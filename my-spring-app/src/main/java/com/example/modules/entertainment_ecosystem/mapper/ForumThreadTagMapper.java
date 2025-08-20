package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.dto.ForumThreadTagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadTagSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ForumThreadTagMapper {

    ForumThreadTagMapper INSTANCE = Mappers.getMapper(ForumThreadTagMapper.class);

    ForumThreadTagDto toDto(ForumThreadTag forumthreadtag);

    ForumThreadTagSimpleDto toSimpleDto(ForumThreadTag forumthreadtag);

    @InheritInverseConfiguration
    ForumThreadTag toEntity(ForumThreadTagDto forumthreadtagDto);

    List<ForumThreadTagDto> toDtoList(List<ForumThreadTag> forumthreadtagList);

    List<ForumThreadTag> toEntityList(List<ForumThreadTagDto> forumthreadtagDtoList);

    void updateEntityFromDto(ForumThreadTagDto dto, @MappingTarget ForumThreadTag entity);

}