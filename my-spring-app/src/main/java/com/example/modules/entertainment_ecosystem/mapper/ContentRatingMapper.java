package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentRating;
import com.example.modules.entertainment_ecosystem.dto.ContentRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentRatingMapper extends BaseMapper<ContentRating, ContentRatingDto, ContentRatingSimpleDto> {
}