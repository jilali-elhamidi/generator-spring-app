package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.VideoGameRating;
import com.example.modules.entertainment_ecosystem.dto.VideoGameRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameRatingSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface VideoGameRatingMapper extends BaseMapper<VideoGameRating, VideoGameRatingDto, VideoGameRatingSimpleDto> {
}