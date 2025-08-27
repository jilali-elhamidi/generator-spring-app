package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SubscriptionFeature;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionFeatureDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionFeatureSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SubscriptionFeatureMapper extends BaseMapper<SubscriptionFeature, SubscriptionFeatureDto, SubscriptionFeatureSimpleDto> {
}