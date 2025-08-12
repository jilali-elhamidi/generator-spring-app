package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PlaylistItem;
import com.example.modules.entertainment_ecosystem.dto.PlaylistItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistItemSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PlaylistItemMapper {

    PlaylistItemMapper INSTANCE = Mappers.getMapper(PlaylistItemMapper.class);

    PlaylistItemDto toDto(PlaylistItem playlistitem);

    PlaylistItemSimpleDto toSimpleDto(PlaylistItem playlistitem);

    @InheritInverseConfiguration
    PlaylistItem toEntity(PlaylistItemDto playlistitemDto);

    List<PlaylistItemDto> toDtoList(List<PlaylistItem> playlistitemList);

    List<PlaylistItem> toEntityList(List<PlaylistItemDto> playlistitemDtoList);

}