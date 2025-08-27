package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.dto.StreamingContentLicenseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface StreamingContentLicenseMapper extends BaseMapper<StreamingContentLicense, StreamingContentLicenseDto, StreamingContentLicenseSimpleDto> {
}