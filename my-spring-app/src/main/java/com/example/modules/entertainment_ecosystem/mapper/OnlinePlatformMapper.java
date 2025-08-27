package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.OnlinePlatform;
import com.example.modules.entertainment_ecosystem.dto.OnlinePlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.OnlinePlatformSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface OnlinePlatformMapper extends BaseMapper<OnlinePlatform, OnlinePlatformDto, OnlinePlatformSimpleDto> {
}