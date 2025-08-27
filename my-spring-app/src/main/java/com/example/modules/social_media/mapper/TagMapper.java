package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Tag;
import com.example.modules.social_media.dto.TagDto;
import com.example.modules.social_media.dtosimple.TagSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends BaseMapper<Tag, TagDto, TagSimpleDto> {
}