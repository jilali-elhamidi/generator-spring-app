package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.LiveStreamViewer;
import com.example.modules.entertainment_ecosystem.dto.LiveStreamViewerDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LiveStreamViewerSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface LiveStreamViewerMapper extends BaseMapper<LiveStreamViewer, LiveStreamViewerDto, LiveStreamViewerSimpleDto> {
}