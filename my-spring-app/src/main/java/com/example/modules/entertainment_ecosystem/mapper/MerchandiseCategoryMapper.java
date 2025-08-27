package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCategorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseCategoryMapper extends BaseMapper<MerchandiseCategory, MerchandiseCategoryDto, MerchandiseCategorySimpleDto> {
}