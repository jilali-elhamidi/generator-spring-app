package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.StreamingPlatform;
import com.example.modules.entertainment_ecosystem.dto.StreamingPlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingPlatformSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface StreamingPlatformMapper extends BaseMapper<StreamingPlatform, StreamingPlatformDto, StreamingPlatformSimpleDto> {
}