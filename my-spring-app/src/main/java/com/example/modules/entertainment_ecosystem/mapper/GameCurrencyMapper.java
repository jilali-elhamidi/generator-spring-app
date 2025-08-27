package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameCurrency;
import com.example.modules.entertainment_ecosystem.dto.GameCurrencyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameCurrencySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameCurrencyMapper extends BaseMapper<GameCurrency, GameCurrencyDto, GameCurrencySimpleDto> {
}