package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseOrderMapper extends BaseMapper<MerchandiseOrder, MerchandiseOrderDto, MerchandiseOrderSimpleDto> {
}