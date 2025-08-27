package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReviewComment;
import com.example.modules.entertainment_ecosystem.dto.GameReviewCommentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewCommentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameReviewCommentMapper extends BaseMapper<GameReviewComment, GameReviewCommentDto, GameReviewCommentSimpleDto> {
}