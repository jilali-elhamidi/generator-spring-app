package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseMapper extends BaseMapper<Merchandise, MerchandiseDto, MerchandiseSimpleDto> {
}