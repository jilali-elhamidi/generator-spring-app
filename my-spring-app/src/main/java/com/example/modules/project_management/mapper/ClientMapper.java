package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Client;
import com.example.modules.project_management.dto.ClientDto;
import com.example.modules.project_management.dtosimple.ClientSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends BaseMapper<Client, ClientDto, ClientSimpleDto> {
}