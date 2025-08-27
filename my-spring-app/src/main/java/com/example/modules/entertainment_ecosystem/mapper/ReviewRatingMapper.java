package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewRating;
import com.example.modules.entertainment_ecosystem.dto.ReviewRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewRatingSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReviewRatingMapper extends BaseMapper<ReviewRating, ReviewRatingDto, ReviewRatingSimpleDto> {
}