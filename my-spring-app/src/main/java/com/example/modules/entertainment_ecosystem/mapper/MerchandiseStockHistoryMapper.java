package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseStockHistoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseStockHistorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MerchandiseStockHistoryMapper extends BaseMapper<MerchandiseStockHistory, MerchandiseStockHistoryDto, MerchandiseStockHistorySimpleDto> {
}