package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.dto.DigitalPurchaseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DigitalPurchaseMapper {

    DigitalPurchaseMapper INSTANCE = Mappers.getMapper(DigitalPurchaseMapper.class);

    DigitalPurchaseDto toDto(DigitalPurchase digitalpurchase);

    DigitalPurchaseSimpleDto toSimpleDto(DigitalPurchase digitalpurchase);

    @InheritInverseConfiguration
    DigitalPurchase toEntity(DigitalPurchaseDto digitalpurchaseDto);

    List<DigitalPurchaseDto> toDtoList(List<DigitalPurchase> digitalpurchaseList);

    List<DigitalPurchase> toEntityList(List<DigitalPurchaseDto> digitalpurchaseDtoList);

}