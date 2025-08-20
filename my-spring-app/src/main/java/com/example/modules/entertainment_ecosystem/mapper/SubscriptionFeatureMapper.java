package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.SubscriptionFeature;
import com.example.modules.entertainment_ecosystem.dto.SubscriptionFeatureDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionFeatureSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SubscriptionFeatureMapper {

    SubscriptionFeatureMapper INSTANCE = Mappers.getMapper(SubscriptionFeatureMapper.class);

    SubscriptionFeatureDto toDto(SubscriptionFeature subscriptionfeature);

    SubscriptionFeatureSimpleDto toSimpleDto(SubscriptionFeature subscriptionfeature);

    @InheritInverseConfiguration
    SubscriptionFeature toEntity(SubscriptionFeatureDto subscriptionfeatureDto);

    List<SubscriptionFeatureDto> toDtoList(List<SubscriptionFeature> subscriptionfeatureList);

    List<SubscriptionFeature> toEntityList(List<SubscriptionFeatureDto> subscriptionfeatureDtoList);

    void updateEntityFromDto(SubscriptionFeatureDto dto, @MappingTarget SubscriptionFeature entity);

}