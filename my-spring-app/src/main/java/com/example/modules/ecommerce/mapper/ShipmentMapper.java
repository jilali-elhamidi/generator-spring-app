package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Shipment;
import com.example.modules.ecommerce.dto.ShipmentDto;
import com.example.modules.ecommerce.dtosimple.ShipmentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    ShipmentDto toDto(Shipment shipment);

    ShipmentSimpleDto toSimpleDto(Shipment shipment);

    @InheritInverseConfiguration
    Shipment toEntity(ShipmentDto shipmentDto);

    List<ShipmentDto> toDtoList(List<Shipment> shipmentList);

    List<Shipment> toEntityList(List<ShipmentDto> shipmentDtoList);

    void updateEntityFromDto(ShipmentDto dto, @MappingTarget Shipment entity);

}