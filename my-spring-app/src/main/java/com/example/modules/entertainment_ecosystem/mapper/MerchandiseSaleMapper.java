package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseSaleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSaleSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseSaleMapper extends BaseMapper<MerchandiseSale, MerchandiseSaleDto, MerchandiseSaleSimpleDto> {
}