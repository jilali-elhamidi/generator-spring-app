package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Warehouse;
import com.example.modules.entertainment_ecosystem.dto.WarehouseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.WarehouseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface WarehouseMapper extends BaseMapper<Warehouse, WarehouseDto, WarehouseSimpleDto> {
}