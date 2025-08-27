package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import com.example.modules.entertainment_ecosystem.dto.PlaylistItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistItemSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface PlaylistItemMapper extends BaseMapper<PlaylistItem, PlaylistItemDto, PlaylistItemSimpleDto> {
}