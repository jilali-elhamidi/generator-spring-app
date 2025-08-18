package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.dto.OrderDto;
import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);

    OrderSimpleDto toSimpleDto(Order order);

    @InheritInverseConfiguration
    Order toEntity(OrderDto orderDto);

    List<OrderDto> toDtoList(List<Order> orderList);

    List<Order> toEntityList(List<OrderDto> orderDtoList);

    void updateEntityFromDto(OrderDto dto, @MappingTarget Order entity);

}