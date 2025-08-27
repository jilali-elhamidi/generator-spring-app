package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentRatingBoard;
import com.example.modules.entertainment_ecosystem.dto.ContentRatingBoardDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentRatingBoardSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentRatingBoardMapper extends BaseMapper<ContentRatingBoard, ContentRatingBoardDto, ContentRatingBoardSimpleDto> {
}