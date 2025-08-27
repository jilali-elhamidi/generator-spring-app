package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentRatingAgeGroup;
import com.example.modules.entertainment_ecosystem.dto.ContentRatingAgeGroupDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingAgeGroupSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentRatingAgeGroupMapper extends BaseMapper<ContentRatingAgeGroup, ContentRatingAgeGroupDto, ContentRatingAgeGroupSimpleDto> {
}