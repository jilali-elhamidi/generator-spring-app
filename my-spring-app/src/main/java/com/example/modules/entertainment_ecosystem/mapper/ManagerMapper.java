package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Manager;
import com.example.modules.entertainment_ecosystem.dto.ManagerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ManagerSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ManagerMapper extends BaseMapper<Manager, ManagerDto, ManagerSimpleDto> {
}