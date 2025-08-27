package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlatform;
import com.example.modules.entertainment_ecosystem.dto.GamePlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GamePlatformMapper extends BaseMapper<GamePlatform, GamePlatformDto, GamePlatformSimpleDto> {
}