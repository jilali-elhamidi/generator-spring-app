package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.dto.FeatureFlagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.FeatureFlagSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface FeatureFlagMapper extends BaseMapper<FeatureFlag, FeatureFlagDto, FeatureFlagSimpleDto> {
}