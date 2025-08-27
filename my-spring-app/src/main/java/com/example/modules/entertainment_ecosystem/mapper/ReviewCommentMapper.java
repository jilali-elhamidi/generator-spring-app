package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewComment;
import com.example.modules.entertainment_ecosystem.dto.ReviewCommentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewCommentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReviewCommentMapper extends BaseMapper<ReviewComment, ReviewCommentDto, ReviewCommentSimpleDto> {
}