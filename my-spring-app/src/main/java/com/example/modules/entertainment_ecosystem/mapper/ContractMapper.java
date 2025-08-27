package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Contract;
import com.example.modules.entertainment_ecosystem.dto.ContractDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContractSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ContractMapper extends BaseMapper<Contract, ContractDto, ContractSimpleDto> {
}