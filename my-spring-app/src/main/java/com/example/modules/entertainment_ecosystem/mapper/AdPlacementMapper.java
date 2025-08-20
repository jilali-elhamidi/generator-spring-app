package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.AdPlacement;
import com.example.modules.entertainment_ecosystem.dto.AdPlacementDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AdPlacementSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AdPlacementMapper {

    AdPlacementMapper INSTANCE = Mappers.getMapper(AdPlacementMapper.class);

    AdPlacementDto toDto(AdPlacement adplacement);

    AdPlacementSimpleDto toSimpleDto(AdPlacement adplacement);

    @InheritInverseConfiguration
    AdPlacement toEntity(AdPlacementDto adplacementDto);

    List<AdPlacementDto> toDtoList(List<AdPlacement> adplacementList);

    List<AdPlacement> toEntityList(List<AdPlacementDto> adplacementDtoList);

    void updateEntityFromDto(AdPlacementDto dto, @MappingTarget AdPlacement entity);

}