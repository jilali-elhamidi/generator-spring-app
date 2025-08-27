package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.dto.LicenseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LicenseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface LicenseMapper extends BaseMapper<License, LicenseDto, LicenseSimpleDto> {
}