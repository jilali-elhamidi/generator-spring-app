package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewLike;
import com.example.modules.entertainment_ecosystem.dto.ReviewLikeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewLikeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReviewLikeMapper extends BaseMapper<ReviewLike, ReviewLikeDto, ReviewLikeSimpleDto> {
}