package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingStatusDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingStatusSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseShippingStatusMapper extends BaseMapper<MerchandiseShippingStatus, MerchandiseShippingStatusDto, MerchandiseShippingStatusSimpleDto> {
}