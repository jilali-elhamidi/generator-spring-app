package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.dto.CategoryDto;
import com.example.modules.ecommerce.dtosimple.CategorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(Category category);

    CategorySimpleDto toSimpleDto(Category category);

    @InheritInverseConfiguration
    Category toEntity(CategoryDto categoryDto);

    List<CategoryDto> toDtoList(List<Category> categoryList);

    List<Category> toEntityList(List<CategoryDto> categoryDtoList);

}