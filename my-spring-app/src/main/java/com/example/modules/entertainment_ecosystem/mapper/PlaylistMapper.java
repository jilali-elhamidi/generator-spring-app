package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.dto.PlaylistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PlaylistMapper extends BaseMapper<Playlist, PlaylistDto, PlaylistSimpleDto> {
}