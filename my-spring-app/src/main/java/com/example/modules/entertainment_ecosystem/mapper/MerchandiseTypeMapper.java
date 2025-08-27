package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseTypeMapper extends BaseMapper<MerchandiseType, MerchandiseTypeDto, MerchandiseTypeSimpleDto> {
}