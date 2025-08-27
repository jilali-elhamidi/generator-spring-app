package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.dto.MessageDto;
import com.example.modules.social_media.dtosimple.MessageSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends BaseMapper<Message, MessageDto, MessageSimpleDto> {
}