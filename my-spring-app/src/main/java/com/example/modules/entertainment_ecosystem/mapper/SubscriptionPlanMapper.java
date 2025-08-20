package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SubscriptionPlan;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionPlanDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionPlanSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SubscriptionPlanMapper {

    SubscriptionPlanMapper INSTANCE = Mappers.getMapper(SubscriptionPlanMapper.class);

    SubscriptionPlanDto toDto(SubscriptionPlan subscriptionplan);

    SubscriptionPlanSimpleDto toSimpleDto(SubscriptionPlan subscriptionplan);

    @InheritInverseConfiguration
    SubscriptionPlan toEntity(SubscriptionPlanDto subscriptionplanDto);

    List<SubscriptionPlanDto> toDtoList(List<SubscriptionPlan> subscriptionplanList);

    List<SubscriptionPlan> toEntityList(List<SubscriptionPlanDto> subscriptionplanDtoList);

    void updateEntityFromDto(SubscriptionPlanDto dto, @MappingTarget SubscriptionPlan entity);

}