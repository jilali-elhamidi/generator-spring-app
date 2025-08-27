package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentLicenseType;
import com.example.modules.entertainment_ecosystem.dto.ContentLicenseTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentLicenseTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContentLicenseTypeMapper extends BaseMapper<ContentLicenseType, ContentLicenseTypeDto, ContentLicenseTypeSimpleDto> {
}