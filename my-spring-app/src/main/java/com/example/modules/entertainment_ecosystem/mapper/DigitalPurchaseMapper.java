package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.dto.DigitalPurchaseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DigitalPurchaseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DigitalPurchaseMapper extends BaseMapper<DigitalPurchase, DigitalPurchaseDto, DigitalPurchaseSimpleDto> {
}