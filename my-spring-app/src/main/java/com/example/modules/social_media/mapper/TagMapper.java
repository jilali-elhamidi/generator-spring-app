package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface TagMapper {

TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);


    

TagDto toDto(Tag tag);

Tag toEntity(TagDto tagDto);

List<TagDto> toDtoList(List<Tag> tagList);

    List<Tag> toEntityList(List<TagDto> tagDtoList);
        }