package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.dto.MusicLabelDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicLabelSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MusicLabelMapper extends BaseMapper<MusicLabel, MusicLabelDto, MusicLabelSimpleDto> {
}