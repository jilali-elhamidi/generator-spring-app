package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionPlanDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SubscriptionPlanMapper extends BaseMapper<SubscriptionPlan, SubscriptionPlanDto, SubscriptionPlanSimpleDto> {
}