package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseShippingMapper {

    MerchandiseShippingMapper INSTANCE = Mappers.getMapper(MerchandiseShippingMapper.class);

    MerchandiseShippingDto toDto(MerchandiseShipping merchandiseshipping);

    MerchandiseShippingSimpleDto toSimpleDto(MerchandiseShipping merchandiseshipping);

    @InheritInverseConfiguration
    MerchandiseShipping toEntity(MerchandiseShippingDto merchandiseshippingDto);

    List<MerchandiseShippingDto> toDtoList(List<MerchandiseShipping> merchandiseshippingList);

    List<MerchandiseShipping> toEntityList(List<MerchandiseShippingDto> merchandiseshippingDtoList);

    void updateEntityFromDto(MerchandiseShippingDto dto, @MappingTarget MerchandiseShipping entity);

}