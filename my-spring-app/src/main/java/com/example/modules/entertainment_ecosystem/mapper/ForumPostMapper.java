package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.dto.ForumPostDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ForumPostMapper {

    ForumPostMapper INSTANCE = Mappers.getMapper(ForumPostMapper.class);

    ForumPostDto toDto(ForumPost forumpost);

    ForumPostSimpleDto toSimpleDto(ForumPost forumpost);

    @InheritInverseConfiguration
    ForumPost toEntity(ForumPostDto forumpostDto);

    List<ForumPostDto> toDtoList(List<ForumPost> forumpostList);

    List<ForumPost> toEntityList(List<ForumPostDto> forumpostDtoList);

    void updateEntityFromDto(ForumPostDto dto, @MappingTarget ForumPost entity);

}