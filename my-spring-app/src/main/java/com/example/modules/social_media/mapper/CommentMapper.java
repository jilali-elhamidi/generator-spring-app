package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Comment;
import com.example.modules.social_media.dto.CommentDto;
import com.example.modules.social_media.dtosimple.CommentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentDto, CommentSimpleDto> {
}