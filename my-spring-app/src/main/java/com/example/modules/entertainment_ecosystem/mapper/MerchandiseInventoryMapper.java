package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseInventoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseInventorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseInventoryMapper {

    MerchandiseInventoryMapper INSTANCE = Mappers.getMapper(MerchandiseInventoryMapper.class);

    MerchandiseInventoryDto toDto(MerchandiseInventory merchandiseinventory);

    MerchandiseInventorySimpleDto toSimpleDto(MerchandiseInventory merchandiseinventory);

    @InheritInverseConfiguration
    MerchandiseInventory toEntity(MerchandiseInventoryDto merchandiseinventoryDto);

    List<MerchandiseInventoryDto> toDtoList(List<MerchandiseInventory> merchandiseinventoryList);

    List<MerchandiseInventory> toEntityList(List<MerchandiseInventoryDto> merchandiseinventoryDtoList);

}