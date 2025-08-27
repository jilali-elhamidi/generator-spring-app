package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingMethod;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingMethodDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingMethodSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseShippingMethodMapper extends BaseMapper<MerchandiseShippingMethod, MerchandiseShippingMethodDto, MerchandiseShippingMethodSimpleDto> {
}