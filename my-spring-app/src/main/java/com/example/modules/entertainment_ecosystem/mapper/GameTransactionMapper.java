package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.GameTransaction;
import com.example.modules.entertainment_ecosystem.dto.GameTransactionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GameTransactionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GameTransactionMapper extends BaseMapper<GameTransaction, GameTransactionDto, GameTransactionSimpleDto> {
}