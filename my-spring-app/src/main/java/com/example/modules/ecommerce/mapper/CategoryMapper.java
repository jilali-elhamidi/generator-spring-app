package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Category;
import com.example.modules.ecommerce.dto.CategoryDto;
import com.example.modules.ecommerce.dtosimple.CategorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDto, CategorySimpleDto> {
}