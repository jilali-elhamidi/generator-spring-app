package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameReview;
import com.example.modules.entertainment_ecosystem.dto.GameReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameReviewSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameReviewMapper extends BaseMapper<GameReview, GameReviewDto, GameReviewSimpleDto> {
}