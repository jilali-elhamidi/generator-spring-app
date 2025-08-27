package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.dto.MessageThreadDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MessageThreadSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MessageThreadMapper extends BaseMapper<MessageThread, MessageThreadDto, MessageThreadSimpleDto> {
}