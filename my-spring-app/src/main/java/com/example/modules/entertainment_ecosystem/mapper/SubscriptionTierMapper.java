package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionTierDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionTierSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SubscriptionTierMapper extends BaseMapper<SubscriptionTier, SubscriptionTierDto, SubscriptionTierSimpleDto> {
}