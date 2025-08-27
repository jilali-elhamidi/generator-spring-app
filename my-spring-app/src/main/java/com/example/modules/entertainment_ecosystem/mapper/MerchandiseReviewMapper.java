package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseReviewSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseReviewMapper extends BaseMapper<MerchandiseReview, MerchandiseReviewDto, MerchandiseReviewSimpleDto> {
}