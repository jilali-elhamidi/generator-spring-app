package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Comment;
import com.example.modules.project_management.dto.CommentDto;
import com.example.modules.project_management.dtosimple.CommentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentDto, CommentSimpleDto> {
}