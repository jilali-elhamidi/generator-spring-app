package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseSupplierDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSupplierSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseSupplierMapper {

    MerchandiseSupplierMapper INSTANCE = Mappers.getMapper(MerchandiseSupplierMapper.class);

    MerchandiseSupplierDto toDto(MerchandiseSupplier merchandisesupplier);

    MerchandiseSupplierSimpleDto toSimpleDto(MerchandiseSupplier merchandisesupplier);

    @InheritInverseConfiguration
    MerchandiseSupplier toEntity(MerchandiseSupplierDto merchandisesupplierDto);

    List<MerchandiseSupplierDto> toDtoList(List<MerchandiseSupplier> merchandisesupplierList);

    List<MerchandiseSupplier> toEntityList(List<MerchandiseSupplierDto> merchandisesupplierDtoList);

}