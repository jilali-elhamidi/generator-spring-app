package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.dto.UserPlaylistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserPlaylistMapper extends BaseMapper<UserPlaylist, UserPlaylistDto, UserPlaylistSimpleDto> {
}