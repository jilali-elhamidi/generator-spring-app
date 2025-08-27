package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.dto.VideoGameDto;
import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface VideoGameMapper extends BaseMapper<VideoGame, VideoGameDto, VideoGameSimpleDto> {
}