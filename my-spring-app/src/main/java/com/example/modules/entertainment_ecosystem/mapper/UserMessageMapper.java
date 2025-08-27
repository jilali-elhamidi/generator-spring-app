package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.dto.UserMessageDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserMessageSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserMessageMapper extends BaseMapper<UserMessage, UserMessageDto, UserMessageSimpleDto> {
}