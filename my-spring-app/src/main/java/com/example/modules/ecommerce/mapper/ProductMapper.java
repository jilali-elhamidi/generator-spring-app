package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.dto.ProductDto;
import com.example.modules.ecommerce.dtosimple.ProductSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDto, ProductSimpleDto> {
}