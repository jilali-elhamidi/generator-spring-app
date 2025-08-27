package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.dto.ContentTagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentTagSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentTagMapper extends BaseMapper<ContentTag, ContentTagDto, ContentTagSimpleDto> {
}