package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.dto.ShipmentDto;
import com.example.modules.ecommerce.dtosimple.ShipmentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ShipmentMapper extends BaseMapper<Shipment, ShipmentDto, ShipmentSimpleDto> {
}