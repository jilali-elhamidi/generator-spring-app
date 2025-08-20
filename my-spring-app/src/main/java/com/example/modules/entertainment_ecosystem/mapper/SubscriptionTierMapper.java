package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionTierDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionTierSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SubscriptionTierMapper {

    SubscriptionTierMapper INSTANCE = Mappers.getMapper(SubscriptionTierMapper.class);

    SubscriptionTierDto toDto(SubscriptionTier subscriptiontier);

    SubscriptionTierSimpleDto toSimpleDto(SubscriptionTier subscriptiontier);

    @InheritInverseConfiguration
    SubscriptionTier toEntity(SubscriptionTierDto subscriptiontierDto);

    List<SubscriptionTierDto> toDtoList(List<SubscriptionTier> subscriptiontierList);

    List<SubscriptionTier> toEntityList(List<SubscriptionTierDto> subscriptiontierDtoList);

    void updateEntityFromDto(SubscriptionTierDto dto, @MappingTarget SubscriptionTier entity);

}