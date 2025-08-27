package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReviewDownvote;
import com.example.modules.entertainment_ecosystem.dto.GameReviewDownvoteDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewDownvoteSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameReviewDownvoteMapper extends BaseMapper<GameReviewDownvote, GameReviewDownvoteDto, GameReviewDownvoteSimpleDto> {
}