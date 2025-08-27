package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.dto.EpisodeReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeReviewSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface EpisodeReviewMapper extends BaseMapper<EpisodeReview, EpisodeReviewDto, EpisodeReviewSimpleDto> {
}