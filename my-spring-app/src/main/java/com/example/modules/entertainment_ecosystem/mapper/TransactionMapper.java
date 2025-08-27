package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.dto.TransactionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.TransactionSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends BaseMapper<Transaction, TransactionDto, TransactionSimpleDto> {
}