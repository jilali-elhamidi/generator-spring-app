package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Playlist;
import com.example.modules.entertainment_ecosystem.dto.PlaylistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    PlaylistDto toDto(Playlist playlist);

    PlaylistSimpleDto toSimpleDto(Playlist playlist);

    @InheritInverseConfiguration
    Playlist toEntity(PlaylistDto playlistDto);

    List<PlaylistDto> toDtoList(List<Playlist> playlistList);

    List<Playlist> toEntityList(List<PlaylistDto> playlistDtoList);

    void updateEntityFromDto(PlaylistDto dto, @MappingTarget Playlist entity);

}