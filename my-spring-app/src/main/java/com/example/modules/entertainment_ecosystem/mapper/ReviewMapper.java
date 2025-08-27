package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.dto.ReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper extends BaseMapper<Review, ReviewDto, ReviewSimpleDto> {
}