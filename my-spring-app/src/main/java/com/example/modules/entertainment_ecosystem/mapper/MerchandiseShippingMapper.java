package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseShippingMapper extends BaseMapper<MerchandiseShipping, MerchandiseShippingDto, MerchandiseShippingSimpleDto> {
}