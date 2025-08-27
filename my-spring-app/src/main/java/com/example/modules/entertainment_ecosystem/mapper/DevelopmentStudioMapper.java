package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.dto.DevelopmentStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DevelopmentStudioSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DevelopmentStudioMapper extends BaseMapper<DevelopmentStudio, DevelopmentStudioDto, DevelopmentStudioSimpleDto> {
}