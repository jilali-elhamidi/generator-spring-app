package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.dto.ForumThreadDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ForumThreadMapper extends BaseMapper<ForumThread, ForumThreadDto, ForumThreadSimpleDto> {
}