package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends BaseMapper<Subscription, SubscriptionDto, SubscriptionSimpleDto> {
}