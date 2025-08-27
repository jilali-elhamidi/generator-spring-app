package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderItemSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseOrderItemMapper extends BaseMapper<MerchandiseOrderItem, MerchandiseOrderItemDto, MerchandiseOrderItemSimpleDto> {
}