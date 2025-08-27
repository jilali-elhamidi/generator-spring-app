package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Order;
import com.example.modules.ecommerce.dto.OrderDto;
import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderDto, OrderSimpleDto> {
}