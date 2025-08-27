package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserRating;
import com.example.modules.entertainment_ecosystem.dto.UserRatingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserRatingSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserRatingMapper extends BaseMapper<UserRating, UserRatingDto, UserRatingSimpleDto> {
}