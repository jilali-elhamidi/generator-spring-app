package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ForumThreadTag;
import com.example.modules.entertainment_ecosystem.dto.ForumThreadTagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadTagSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ForumThreadTagMapper extends BaseMapper<ForumThreadTag, ForumThreadTagDto, ForumThreadTagSimpleDto> {
}