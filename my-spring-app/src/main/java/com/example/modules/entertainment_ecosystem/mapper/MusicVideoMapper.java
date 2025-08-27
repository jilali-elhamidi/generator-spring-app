package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicVideo;
import com.example.modules.entertainment_ecosystem.dto.MusicVideoDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicVideoSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MusicVideoMapper extends BaseMapper<MusicVideo, MusicVideoDto, MusicVideoSimpleDto> {
}