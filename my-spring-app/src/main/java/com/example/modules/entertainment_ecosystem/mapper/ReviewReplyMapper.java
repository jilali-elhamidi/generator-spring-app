package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.dto.ReviewReplyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewReplySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReviewReplyMapper extends BaseMapper<ReviewReply, ReviewReplyDto, ReviewReplySimpleDto> {
}