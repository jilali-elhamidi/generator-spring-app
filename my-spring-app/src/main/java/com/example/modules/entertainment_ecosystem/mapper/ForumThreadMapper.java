package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.dto.ForumThreadDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ForumThreadMapper {

    ForumThreadMapper INSTANCE = Mappers.getMapper(ForumThreadMapper.class);

    ForumThreadDto toDto(ForumThread forumthread);

    ForumThreadSimpleDto toSimpleDto(ForumThread forumthread);

    @InheritInverseConfiguration
    ForumThread toEntity(ForumThreadDto forumthreadDto);

    List<ForumThreadDto> toDtoList(List<ForumThread> forumthreadList);

    List<ForumThread> toEntityList(List<ForumThreadDto> forumthreadDtoList);

}