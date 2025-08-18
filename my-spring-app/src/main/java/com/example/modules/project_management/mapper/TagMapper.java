package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Tag;
import com.example.modules.project_management.dto.TagDto;
import com.example.modules.project_management.dtosimple.TagSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto toDto(Tag tag);

    TagSimpleDto toSimpleDto(Tag tag);

    @InheritInverseConfiguration
    Tag toEntity(TagDto tagDto);

    List<TagDto> toDtoList(List<Tag> tagList);

    List<Tag> toEntityList(List<TagDto> tagDtoList);

    void updateEntityFromDto(TagDto dto, @MappingTarget Tag entity);

}