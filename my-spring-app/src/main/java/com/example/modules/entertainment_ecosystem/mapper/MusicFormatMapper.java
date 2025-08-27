package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.dto.MusicFormatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicFormatSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MusicFormatMapper extends BaseMapper<MusicFormat, MusicFormatDto, MusicFormatSimpleDto> {
}