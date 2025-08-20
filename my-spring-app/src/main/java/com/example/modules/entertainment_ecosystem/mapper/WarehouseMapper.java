package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.dto.WarehouseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.WarehouseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    WarehouseDto toDto(Warehouse warehouse);

    WarehouseSimpleDto toSimpleDto(Warehouse warehouse);

    @InheritInverseConfiguration
    Warehouse toEntity(WarehouseDto warehouseDto);

    List<WarehouseDto> toDtoList(List<Warehouse> warehouseList);

    List<Warehouse> toEntityList(List<WarehouseDto> warehouseDtoList);

    void updateEntityFromDto(WarehouseDto dto, @MappingTarget Warehouse entity);

}