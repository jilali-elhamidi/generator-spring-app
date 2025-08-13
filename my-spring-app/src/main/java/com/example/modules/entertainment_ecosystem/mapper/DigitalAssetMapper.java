package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import com.example.modules.entertainment_ecosystem.dto.DigitalAssetDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DigitalAssetMapper {

    DigitalAssetMapper INSTANCE = Mappers.getMapper(DigitalAssetMapper.class);

    DigitalAssetDto toDto(DigitalAsset digitalasset);

    DigitalAssetSimpleDto toSimpleDto(DigitalAsset digitalasset);

    @InheritInverseConfiguration
    DigitalAsset toEntity(DigitalAssetDto digitalassetDto);

    List<DigitalAssetDto> toDtoList(List<DigitalAsset> digitalassetList);

    List<DigitalAsset> toEntityList(List<DigitalAssetDto> digitalassetDtoList);

}