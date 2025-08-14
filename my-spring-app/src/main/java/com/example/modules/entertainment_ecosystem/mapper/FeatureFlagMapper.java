package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import com.example.modules.entertainment_ecosystem.dto.FeatureFlagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.FeatureFlagSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface FeatureFlagMapper {

    FeatureFlagMapper INSTANCE = Mappers.getMapper(FeatureFlagMapper.class);

    FeatureFlagDto toDto(FeatureFlag featureflag);

    FeatureFlagSimpleDto toSimpleDto(FeatureFlag featureflag);

    @InheritInverseConfiguration
    FeatureFlag toEntity(FeatureFlagDto featureflagDto);

    List<FeatureFlagDto> toDtoList(List<FeatureFlag> featureflagList);

    List<FeatureFlag> toEntityList(List<FeatureFlagDto> featureflagDtoList);

    void updateEntityFromDto(FeatureFlagDto dto, @MappingTarget FeatureFlag entity);

}