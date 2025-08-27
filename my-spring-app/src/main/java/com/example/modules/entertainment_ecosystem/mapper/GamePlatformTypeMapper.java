package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GamePlatformType;
import com.example.modules.entertainment_ecosystem.dto.GamePlatformTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GamePlatformTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GamePlatformTypeMapper extends BaseMapper<GamePlatformType, GamePlatformTypeDto, GamePlatformTypeSimpleDto> {
}