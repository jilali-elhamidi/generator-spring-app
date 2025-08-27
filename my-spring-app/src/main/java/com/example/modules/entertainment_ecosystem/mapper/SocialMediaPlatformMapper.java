package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SocialMediaPlatform;
import com.example.modules.entertainment_ecosystem.dto.SocialMediaPlatformDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SocialMediaPlatformSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SocialMediaPlatformMapper extends BaseMapper<SocialMediaPlatform, SocialMediaPlatformDto, SocialMediaPlatformSimpleDto> {
}