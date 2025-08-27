package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.OrderItem;
import com.example.modules.ecommerce.dto.OrderItemDto;
import com.example.modules.ecommerce.dtosimple.OrderItemSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper extends BaseMapper<OrderItem, OrderItemDto, OrderItemSimpleDto> {
}