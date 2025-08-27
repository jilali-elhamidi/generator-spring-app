package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.dto.ContentProviderDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentProviderSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentProviderMapper extends BaseMapper<ContentProvider, ContentProviderDto, ContentProviderSimpleDto> {
}