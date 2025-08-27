package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.LiveStream;
import com.example.modules.entertainment_ecosystem.dto.LiveStreamDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface LiveStreamMapper extends BaseMapper<LiveStream, LiveStreamDto, LiveStreamSimpleDto> {
}