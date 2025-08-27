package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Post;
import com.example.modules.social_media.dto.PostDto;
import com.example.modules.social_media.dtosimple.PostSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends BaseMapper<Post, PostDto, PostSimpleDto> {
}