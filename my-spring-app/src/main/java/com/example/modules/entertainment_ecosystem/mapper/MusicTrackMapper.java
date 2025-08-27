package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.dto.MusicTrackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MusicTrackMapper extends BaseMapper<MusicTrack, MusicTrackDto, MusicTrackSimpleDto> {
}