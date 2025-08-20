package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingMethodDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingMethodSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseShippingMethodMapper {

    MerchandiseShippingMethodMapper INSTANCE = Mappers.getMapper(MerchandiseShippingMethodMapper.class);

    MerchandiseShippingMethodDto toDto(MerchandiseShippingMethod merchandiseshippingmethod);

    MerchandiseShippingMethodSimpleDto toSimpleDto(MerchandiseShippingMethod merchandiseshippingmethod);

    @InheritInverseConfiguration
    MerchandiseShippingMethod toEntity(MerchandiseShippingMethodDto merchandiseshippingmethodDto);

    List<MerchandiseShippingMethodDto> toDtoList(List<MerchandiseShippingMethod> merchandiseshippingmethodList);

    List<MerchandiseShippingMethod> toEntityList(List<MerchandiseShippingMethodDto> merchandiseshippingmethodDtoList);

    void updateEntityFromDto(MerchandiseShippingMethodDto dto, @MappingTarget MerchandiseShippingMethod entity);

}