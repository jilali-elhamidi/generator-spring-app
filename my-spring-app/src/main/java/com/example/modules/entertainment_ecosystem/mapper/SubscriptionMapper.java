package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Subscription;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionDto toDto(Subscription subscription);

    SubscriptionSimpleDto toSimpleDto(Subscription subscription);

    @InheritInverseConfiguration
    Subscription toEntity(SubscriptionDto subscriptionDto);

    List<SubscriptionDto> toDtoList(List<Subscription> subscriptionList);

    List<Subscription> toEntityList(List<SubscriptionDto> subscriptionDtoList);

    void updateEntityFromDto(SubscriptionDto dto, @MappingTarget Subscription entity);

}