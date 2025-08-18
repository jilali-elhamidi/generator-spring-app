package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Product;
import com.example.modules.ecommerce.dto.ProductDto;
import com.example.modules.ecommerce.dtosimple.ProductSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product product);

    ProductSimpleDto toSimpleDto(Product product);

    @InheritInverseConfiguration
    Product toEntity(ProductDto productDto);

    List<ProductDto> toDtoList(List<Product> productList);

    List<Product> toEntityList(List<ProductDto> productDtoList);

    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);

}