package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.StreamingService;
import com.example.modules.entertainment_ecosystem.dto.StreamingServiceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingServiceSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface StreamingServiceMapper extends BaseMapper<StreamingService, StreamingServiceDto, StreamingServiceSimpleDto> {
}