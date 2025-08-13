package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import com.example.modules.entertainment_ecosystem.dto.DigitalAssetTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalAssetTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DigitalAssetTypeMapper {

    DigitalAssetTypeMapper INSTANCE = Mappers.getMapper(DigitalAssetTypeMapper.class);

    DigitalAssetTypeDto toDto(DigitalAssetType digitalassettype);

    DigitalAssetTypeSimpleDto toSimpleDto(DigitalAssetType digitalassettype);

    @InheritInverseConfiguration
    DigitalAssetType toEntity(DigitalAssetTypeDto digitalassettypeDto);

    List<DigitalAssetTypeDto> toDtoList(List<DigitalAssetType> digitalassettypeList);

    List<DigitalAssetType> toEntityList(List<DigitalAssetTypeDto> digitalassettypeDtoList);

}