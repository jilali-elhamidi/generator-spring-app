package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import com.example.modules.entertainment_ecosystem.dto.ConnectionTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ConnectionTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ConnectionTypeMapper extends BaseMapper<ConnectionType, ConnectionTypeDto, ConnectionTypeSimpleDto> {
}