package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseInventoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseInventorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseInventoryMapper extends BaseMapper<MerchandiseInventory, MerchandiseInventoryDto, MerchandiseInventorySimpleDto> {
}