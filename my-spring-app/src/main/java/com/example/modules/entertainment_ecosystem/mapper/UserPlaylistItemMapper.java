package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.dto.UserPlaylistItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistItemSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface UserPlaylistItemMapper extends BaseMapper<UserPlaylistItem, UserPlaylistItemDto, UserPlaylistItemSimpleDto> {
}