package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.dto.ContentLanguageDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentLanguageSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentLanguageMapper extends BaseMapper<ContentLanguage, ContentLanguageDto, ContentLanguageSimpleDto> {
}