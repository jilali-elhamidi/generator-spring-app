package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReviewUpvote;
import com.example.modules.entertainment_ecosystem.dto.GameReviewUpvoteDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewUpvoteSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameReviewUpvoteMapper extends BaseMapper<GameReviewUpvote, GameReviewUpvoteDto, GameReviewUpvoteSimpleDto> {
}