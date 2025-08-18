package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.dto.OrderItemDto;
import com.example.modules.ecommerce.dtosimple.OrderItemSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDto toDto(OrderItem orderitem);

    OrderItemSimpleDto toSimpleDto(OrderItem orderitem);

    @InheritInverseConfiguration
    OrderItem toEntity(OrderItemDto orderitemDto);

    List<OrderItemDto> toDtoList(List<OrderItem> orderitemList);

    List<OrderItem> toEntityList(List<OrderItemDto> orderitemDtoList);

    void updateEntityFromDto(OrderItemDto dto, @MappingTarget OrderItem entity);

}